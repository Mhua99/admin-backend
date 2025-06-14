package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.SysRolesQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysRoles;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface SysRolesService {
    PageResult<SysRoles> list(SysRolesQueryDTO sysRolesQueryDTO);

    SysRoles insert(SysRoles sysRoles);

    SysRoles update(SysRoles sysRoles);

    Boolean delete(List<Integer> ids);

    SysRoles getById(Integer id);
}
