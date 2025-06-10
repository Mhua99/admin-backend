package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.SysMenuQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysMenu;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface SysMenuService {

    PageResult<SysMenu> list(SysMenuQueryDTO sysMenuQueryDTO);

    SysMenu create(SysMenu sysMenu);

    SysMenu update(SysMenu sysMenu);

    Boolean delete(List<Integer> ids);
}
