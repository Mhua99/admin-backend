package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.SysDeptQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysDept;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysDeptMapper {
    Page<SysDept> list(SysDeptQueryDTO sysDeptQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(SysDept sysDept);

    @AutoFill(OperationType.UPDATE)
    void update(SysDept sysDept);

    SysDept getById(Integer id);

    Boolean deleteByIds(@Param("ids") List<Integer> ids);

    List<SysDept> listAll();
}
