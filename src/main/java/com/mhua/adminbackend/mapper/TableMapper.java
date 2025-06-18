package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.TableDTO;
import com.mhua.adminbackend.pojo.entity.Table;
import com.mhua.adminbackend.pojo.entity.TableField;
import com.mhua.adminbackend.pojo.vo.TableVO;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.UpdateProvider;

import java.util.List;

@Mapper
public interface TableMapper {

    @AutoFill(OperationType.INSERT)
    void insert(Table tableEntity);

    @AutoFill(OperationType.INSERT)
    void insertFields(List<TableField> fieldList);

    @UpdateProvider(type = SqlProvider.class, method = "createTableSql")
    void createTable(StringBuilder sql);

    Page<TableVO> list(TableDTO TableVO);

    Table findByNameOrId(TableDTO tableDTO);

    void update(Table tableEntity);

    List<TableField> findFieldsByTableId(Integer tableId);

    void deleteFieldsByTableId(Integer tableId);

    void delete(Integer id);

    void deleteField(Integer fieldId);

    @DeleteProvider(type = SqlProvider.class, method = "dropPhysicalTable")
    void dropPhysicalTable(String name);

    void getTableAndFields(Integer id);
}
