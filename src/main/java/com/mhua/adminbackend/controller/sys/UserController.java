package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.SysUserLoginDTO;
import com.mhua.adminbackend.pojo.dto.SysUserQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysUser;
import com.mhua.adminbackend.pojo.vo.SysUserVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.SysUserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "用户管理")
@RequestMapping("/sys/user")
public class UserController {

    @Autowired
    private SysUserService sysUserService;

    @GetMapping("/list")
    @Operation(summary = "用户列表")
    public Result<PageResult<SysUser>> list(SysUserQueryDTO sysUserQueryDTO) {
        PageResult<SysUser> pageResult = sysUserService.list(sysUserQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增用户")
    public Result<SysUserVO> create(@RequestBody SysUser sysUser) {
        SysUserVO sysUserRet = sysUserService.create(sysUser);
        return Result.success(sysUserRet);
    }

    @PutMapping
    @Operation(summary = "修改用户")
    public Result<SysUserVO> update(@RequestBody SysUser sysUser) {
        SysUserVO sysUserRet = sysUserService.update(sysUser);
        return Result.success(sysUserRet);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除用户")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        return Result.success(sysUserService.delete(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取用户详情")
    public Result<SysUserVO> getInfo(@PathVariable("id") Integer id) {
        SysUserVO sysUserRet = sysUserService.getById(id);
        return Result.success(sysUserRet);
    }

    /**
     * 登录
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public Result<String> login(@RequestBody SysUserLoginDTO sysUserLoginDTO) {
        return Result.success(sysUserService.login(sysUserLoginDTO));
    }
}
