package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.mapper.SysDeptMapper;
import com.mhua.adminbackend.pojo.dto.SysDeptQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysDept;
import com.mhua.adminbackend.pojo.vo.SysDeptTreeVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.SysDeptService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class SysDeptServiceImpl implements SysDeptService {

    @Autowired
    private SysDeptMapper sysDeptMapper;

    public PageResult<SysDept> list(SysDeptQueryDTO sysDeptQueryDTO) {
        Integer page = sysDeptQueryDTO.getPage();
        Integer pageSize = sysDeptQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<SysDept> pageList = sysDeptMapper.list(sysDeptQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public SysDept insert(SysDept sysDept) {

        String name = sysDept.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("部门名称不能为空");
        }

        /**
         * 插入数据
         */
        sysDeptMapper.insert(sysDept);

        return sysDeptMapper.getById(sysDept.getId());
    }

    public SysDept update(SysDept sysDept) {
        if (sysDept.getId() == null) {
            throw new BaseException("部门ID不能为空", 500);
        }

        String name = sysDept.getName();
        if (name == null || name.isEmpty()) {
            throw new BaseException("部门名称不能为空", 500);
        }

        /**
         * 更新数据
         */
        sysDeptMapper.update(sysDept);

        return sysDeptMapper.getById(sysDept.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return sysDeptMapper.deleteByIds(ids);
    }

    public SysDept getById(Integer id) {
        return sysDeptMapper.getById(id);
    }

    /**
     * 构建部门树
     */
    public List<SysDeptTreeVO> getTree() {
        // 获取所有部门数据
        List<SysDept> allDepts = sysDeptMapper.listAll();

        // 构建部门树
        List<SysDeptTreeVO> tree = new ArrayList<>();
        Map<Integer, SysDeptTreeVO> deptVoMap = new HashMap<>();

        // 将部门数据转换为 VO 并存入 Map
        for (SysDept dept : allDepts) {
            SysDeptTreeVO vo = new SysDeptTreeVO();
            BeanUtils.copyProperties(dept, vo);
            deptVoMap.put(vo.getId(), vo);
        }

        // 遍历构建树形结构
        for (SysDept dept : allDepts) {
            SysDeptTreeVO vo = deptVoMap.get(dept.getId());
            if (dept.getParentId() == null || dept.getParentId() == 0) {
                // 根节点
                tree.add(vo);
            } else {
                // 子节点
                SysDeptTreeVO parent = deptVoMap.get(dept.getParentId());
                if (parent != null) {
                    if (parent.getChildren() == null) {
                        parent.setChildren(new ArrayList<>());
                    }
                    parent.getChildren().add(vo);
                }
            }
        }

        return tree;
    }

}
