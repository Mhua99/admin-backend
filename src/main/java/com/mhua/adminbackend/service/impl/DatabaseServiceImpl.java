package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.TableMapper;
import com.mhua.adminbackend.pojo.dto.TableDTO;
import com.mhua.adminbackend.pojo.entity.Table;
import com.mhua.adminbackend.pojo.entity.TableField;
import com.mhua.adminbackend.pojo.vo.TableVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.DatabaseService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class DatabaseServiceImpl implements DatabaseService {
    @Autowired
    private TableMapper tableMapper;


    @Transactional
    public void insert(TableDTO tableDTO) {

        String name = tableDTO.getName();
        List<TableField> fields = tableDTO.getFields();

        // 查询是否已有同名表
        Table existing = tableMapper.findByNameOrId(tableDTO);
        if (existing != null) {
            throw new RuntimeException("表名已存在: " + name);
        }

        Table tableEntity = new Table();
        tableEntity.setName(name);
        tableEntity.setDesc(tableDTO.getDesc());
        tableMapper.insert(tableEntity); // 插入主表
        // 获取刚插入的主表 ID
        Integer tableId = tableEntity.getId();

        // 设置子表的 tableId 并保存
        List<TableField> fieldList = fields.stream()
                .map(field -> {
                    TableField entity = new TableField();
                    entity.setName(field.getName());
                    entity.setType(field.getType());
                    entity.setConstraints(field.getConstraints());
                    entity.setTableId(tableId); // 设置外键
                    entity.setMeaning(field.getMeaning());
                    return entity;
                })
                .toList();

        tableMapper.insertFields(fieldList);

        /**
         * 并且创建数据库表
         */
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(name).append(" (");

        sql.append("id INT AUTO_INCREMENT PRIMARY KEY, ")
                .append("createTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP, ")
                .append("createUser INT, ");
        for (int i = 0; i < fields.size(); i++) {
            TableField field = fields.get(i);
            sql.append(field.getName()).append(" ").append(field.getType());
            if (field.getConstraints() != null && !field.getConstraints().isEmpty()) {
                sql.append(" ").append(field.getConstraints());
            }
            if (i < fields.size() - 1) {
                sql.append(", ");
            }
        }

        sql.append(");");
        tableMapper.createTable(sql);
    }

    public PageResult<TableVO> list(TableDTO tableDTO) {

        Integer page = tableDTO.getPage();
        Integer pageSize = tableDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<TableVO> pageList = tableMapper.list(tableDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    @Transactional
    public void update(TableDTO tableDTO) {
        Integer tableId = tableDTO.getId();
        if (tableId == null || tableId <= 0) {
            throw new RuntimeException("无效的表ID");
        }

        // 查询现有表信息
        Table existing = tableMapper.findByNameOrId(tableDTO);
        if (existing == null) {
            throw new RuntimeException("找不到指定的表记录: ID = " + tableId);
        }

        // 获取新字段列表
        List<TableField> newFields = tableDTO.getFields();
        for (TableField newField : newFields) {
            if(newField.getName().isEmpty() || newField.getType().isEmpty()) {
                throw new RuntimeException("字段名或字段类型不能为空");
            }
        }

        // 更新主表信息（可选）
        Table tableEntity = new Table();
        tableEntity.setId(tableId);
        tableEntity.setName(tableDTO.getName());
        tableEntity.setDesc(tableDTO.getDesc());
        tableMapper.update(tableEntity);

        // 查询原有字段
        List<TableField> oldFields = tableMapper.findFieldsByTableId(tableId);


        // 找出需新增、删除、修改的字段
        Map<String, TableField> oldFieldMap = oldFields.stream()
                .collect(Collectors.toMap(TableField::getName, f -> f));

        Map<String, TableField> newFieldMap = newFields.stream()
                .collect(Collectors.toMap(TableField::getName, f -> f));

        List<TableField> toAdd = newFields.stream()
                .filter(f -> !oldFieldMap.containsKey(f.getName()))
                .toList();

        List<TableField> toRemove = oldFields.stream()
                .filter(f -> !newFieldMap.containsKey(f.getName()))
                .toList();

        List<TableField> toModify = newFields.stream()
                .filter(f -> oldFieldMap.containsKey(f.getName())
                        && (!f.getType().equals(oldFieldMap.get(f.getName()).getType())
                        || !Objects.equals(f.getConstraints(), oldFieldMap.get(f.getName()).getConstraints())))
                .toList();

        // 构建 ALTER TABLE SQL 并执行
        String tableName = existing.getName();
        List<String> alterSql = generateAlterTableSql(tableName, toAdd, toRemove, toModify);
        for (String sql : alterSql) {
            if (sql == null || sql.trim().isEmpty()) {
                continue;
            }
            StringBuilder stringBuilder = new StringBuilder(sql);
            tableMapper.createTable(stringBuilder); // 每条语句单独执行
        }

        // 更新子表数据：先删除后插入
        tableMapper.deleteFieldsByTableId(tableId);

        List<TableField> fieldList = newFields.stream()
                .map(field -> {
                    TableField entity = new TableField();
                    entity.setName(field.getName());
                    entity.setType(field.getType());
                    entity.setConstraints(field.getConstraints());
                    entity.setTableId(tableId);
                    entity.setMeaning(field.getMeaning());
                    return entity;
                })
                .toList();

        tableMapper.insertFields(fieldList);
    }

    @Transactional
    public Boolean delete(Integer id) {
        if (id == null) {
            throw new RuntimeException("ID不能为空");
        }

        TableDTO tableDTO = new TableDTO();
        tableDTO.setId(id);
        Table existing = tableMapper.findByNameOrId(tableDTO);
        if (existing == null) {
            throw new RuntimeException("找不到指定的表记录: ID = " + id);
        }

        /**
         * 删除子表
         */
        tableMapper.deleteField(id);

        /**
         * 删除主表
         */
        tableMapper.delete(id);


        /**
         * 删除物理数据库表
         */
        tableMapper.dropPhysicalTable(existing.getName());

        return true;
    }

    public TableVO findTableAndFields(Integer id) {
        tableMapper.getTableAndFields(id);
        return null;
    }

    public List<Table> getAllTable() {
        return tableMapper.getAllTable();
    }


    // 修改 generateAlterTableSql 返回类型为 List<String>
    private List<String> generateAlterTableSql(String tableName, List<TableField> toAdd, List<TableField> toRemove, List<TableField> toModify) {
        List<String> sqls = new ArrayList<>();

        // DROP 字段
        for (TableField field : toRemove) {
            sqls.add("ALTER TABLE " + tableName + " DROP COLUMN " + field.getName());
        }

        // ADD 字段
        for (TableField field : toAdd) {
            String sql = "ALTER TABLE " + tableName + " ADD COLUMN " + field.getName() + " " + field.getType();
            if (field.getConstraints() != null && !field.getConstraints().isEmpty()) {
                sql += " " + field.getConstraints();
            }
            sqls.add(sql);
        }

        // MODIFY 字段
        for (TableField field : toModify) {
            String sql = "ALTER TABLE " + tableName + " MODIFY COLUMN " + field.getName() + " " + field.getType();
            if (field.getConstraints() != null && !field.getConstraints().isEmpty()) {
                sql += " " + field.getConstraints();
            }
            sqls.add(sql);
        }

        return sqls;
    }
}
