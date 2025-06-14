package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.SysMenuQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysMenu;
import com.mhua.adminbackend.pojo.entity.SysUser;
import com.mhua.adminbackend.pojo.vo.SysMenuTreeVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.SysMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@Tag(name = "菜单管理")
public class MenuController {

    @Autowired
    private SysMenuService sysMenuService;

    @GetMapping("/list")
    @Operation(summary = "菜单列表")
    public Result<PageResult<SysMenu>> list(SysMenuQueryDTO sysMenuQueryDTO) {
        PageResult<SysMenu> pageResult = sysMenuService.list(sysMenuQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增菜单")
    public Result<SysMenu> insert(@RequestBody SysMenu sysMenu) {
        SysMenu menu = sysMenuService.insert(sysMenu);
        return Result.success(menu);
    }

    @PutMapping
    @Operation(summary = "修改菜单")
    public Result<SysMenu> update(@RequestBody SysMenu sysMenu) {
        SysMenu menu = sysMenuService.update(sysMenu);
        return Result.success(menu);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除菜单")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean result = sysMenuService.delete(ids);
        return Result.success(result);
    }

    @GetMapping("/tree")
    @Operation(summary = "获取菜单树")
    public Result<List<SysMenuTreeVO>> tree() {
        List<SysMenuTreeVO> tree = sysMenuService.tree();
        return Result.success(tree);
    }

}
