package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.SysRolesQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysRoles;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.SysRolesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/roles")
@Tag(name = "角色管理")
public class RolesController {

    @Autowired
    private SysRolesService sysRolesService;

    @GetMapping("/list")
    @Operation(summary = "角色列表")
    public Result<PageResult<SysRoles>> list(SysRolesQueryDTO sysRolesQueryDTO) {
        PageResult<SysRoles> pageResult = sysRolesService.list(sysRolesQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增角色")
    public Result<SysRoles> insert(@RequestBody SysRoles sysRoles) {
        SysRoles role = sysRolesService.insert(sysRoles);
        return Result.success(role);
    }

    @PutMapping
    @Operation(summary = "修改角色")
    public Result<SysRoles> update(@RequestBody SysRoles sysRoles) {
        SysRoles role = sysRolesService.update(sysRoles);
        return Result.success(role);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除角色")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean result = sysRolesService.delete(ids);
        return Result.success(result);
    }

    @GetMapping("/{ids}")
    @Operation(summary = "获取角色")
    public Result<SysRoles> get(@PathVariable("ids") Integer id) {
        SysRoles role = sysRolesService.getById(id);
        return Result.success(role);
    }
}
