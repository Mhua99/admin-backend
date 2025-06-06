package com.mhua.adminbackend.service;

import com.mhua.adminbackend.dto.SysUserQueryDTO;
import com.mhua.adminbackend.result.PageResult;

public interface SysUserService {
    PageResult list(SysUserQueryDTO sysUserQueryDTO);
}
