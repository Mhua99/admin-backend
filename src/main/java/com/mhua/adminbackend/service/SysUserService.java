package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.SysUserLoginDTO;
import com.mhua.adminbackend.pojo.dto.SysUserQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysUser;
import com.mhua.adminbackend.pojo.vo.SysUserLoginVO;
import com.mhua.adminbackend.pojo.vo.SysUserVO;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface SysUserService {
    PageResult list(SysUserQueryDTO sysUserQueryDTO);

    SysUserVO insert(SysUser sysUser);

    SysUserVO update(SysUser sysUser);

    Boolean delete(List<Integer> ids);

    SysUserVO getById(Integer id);

    SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO);

    List<SysUserVO> getAll();
}
