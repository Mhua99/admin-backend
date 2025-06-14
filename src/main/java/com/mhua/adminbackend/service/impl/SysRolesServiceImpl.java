package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.mapper.SysRolesMapper;
import com.mhua.adminbackend.pojo.dto.SysRolesQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysMenu;
import com.mhua.adminbackend.pojo.entity.SysRoles;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.SysRolesService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysRolesServiceImpl implements SysRolesService {

    @Autowired
    private SysRolesMapper sysRolesMapper;

    public PageResult<SysRoles> list(SysRolesQueryDTO sysRolesQueryDTO) {

        Integer page = sysRolesQueryDTO.getPage();
        Integer pageSize = sysRolesQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<SysRoles> pageList = sysRolesMapper.list(sysRolesQueryDTO);

        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public SysRoles insert(SysRoles sysRoles) {
        String name = sysRoles.getName();

        if(name == null || name.isEmpty()) {
            throw new BaseException("角色名称不能为空", 500);
        }

        sysRolesMapper.insert(sysRoles);

        return sysRolesMapper.getById(sysRoles.getId());
    }

    public SysRoles update(SysRoles sysRoles) {
        String name = sysRoles.getName();
        Integer id = sysRoles.getId();

        if(id  == null) {
            throw new BaseException("ID不能为空", 500);
        }

        if(name == null || name.isEmpty()) {
            throw new BaseException("角色名称不能为空", 500);
        }

        sysRolesMapper.update(sysRoles);

        return sysRolesMapper.getById(sysRoles.getId());
    }

    public Boolean delete(List<Integer> ids) {
        if(ids == null || ids.isEmpty()) {
            throw new BaseException("ID不能为空");
        }

        return sysRolesMapper.delete(ids);
    }

    public SysRoles getById(Integer id) {

        if (id == null) {
            throw new BaseException("ID不能为空");
        }

        return sysRolesMapper.getById(id);
    }
}
