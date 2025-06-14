package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.SysRolesQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysRoles;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SysRolesMapper {

    Page<SysRoles> list(SysRolesQueryDTO sysRolesQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(SysRoles sysRoles);

    @AutoFill(OperationType.UPDATE)
    void update(SysRoles sysRoles);

    SysRoles getById(Integer id);

    Boolean delete(List<Integer> ids);
}
