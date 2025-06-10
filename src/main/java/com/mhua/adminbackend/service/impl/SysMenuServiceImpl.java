package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.mapper.SysMenuMapper;
import com.mhua.adminbackend.pojo.dto.SysMenuQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysDept;
import com.mhua.adminbackend.pojo.entity.SysMenu;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.SysMenuService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    public PageResult<SysMenu> list(SysMenuQueryDTO sysMenuQueryDTO) {
        Integer page = sysMenuQueryDTO.getPage();
        Integer pageSize = sysMenuQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);
        PageHelper.startPage(page, pageSize);

        Page<SysMenu> pageList = sysMenuMapper.list(sysMenuQueryDTO);

        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public SysMenu create(SysMenu sysMenu) {

        String name = sysMenu.getName();
        if(name == null || name.isEmpty()) {
            throw new BaseException("菜单名称不能为空");
        }

        sysMenuMapper.insert(sysMenu);

        return sysMenuMapper.getById(sysMenu.getId());
    }

    public SysMenu update(SysMenu sysMenu) {
        if (sysMenu.getId() == null) {
            throw new BaseException("ID不能为空");
        }

        String name = sysMenu.getName();
        if (name == null || name.isEmpty()) {
            throw new RuntimeException("名称不能为空");
        }

        sysMenuMapper.update(sysMenu);

        return sysMenuMapper.getById(sysMenu.getId());
    }

    public Boolean delete(List<Integer> ids) {
        if (ids == null || ids.isEmpty()) {
            throw new BaseException("ID不能为空");
        }

        return sysMenuMapper.delete(ids);
    }
}
