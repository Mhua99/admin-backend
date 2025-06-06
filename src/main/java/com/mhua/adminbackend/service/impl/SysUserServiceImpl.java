package com.mhua.adminbackend.service.impl;

import com.mhua.adminbackend.dto.SysUserQueryDTO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {

//    @Autowired
//    private SysUserMapper  sysUserMapper;

    public PageResult list(SysUserQueryDTO sysUserQueryDTO) {
//        PageHelper.startPage(categoryPageQueryDTO.getPage(),categoryPageQueryDTO.getPageSize());
        return null;
    }
}
