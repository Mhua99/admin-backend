package com.mhua.adminbackend.controller;

import com.mhua.adminbackend.dto.SysUserQueryDTO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sys/user")
public class SysUserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/list")
    public Result<PageResult> list(SysUserQueryDTO sysUserQueryDTO) {
//        PageResult pageResult = sysUserService.list(sysUserQueryDTO);
        return Result.success();
    }
}
