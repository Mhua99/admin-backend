package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.SysDeptQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysDept;
import com.mhua.adminbackend.pojo.vo.SysDeptTreeVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.SysDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/sys/dept")
@Tag(name = "部门管理")
public class DeptController {

    @Autowired
    private SysDeptService sysDeptService;

    @GetMapping("/list")
    @Operation(summary = "部门列表")
    public Result<PageResult<SysDept>> list(SysDeptQueryDTO sysDeptQueryDTO) {
        PageResult<SysDept> pageResult = sysDeptService.list(sysDeptQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增部门")
    public Result<SysDept> create(@RequestBody SysDept sysDept) {
        SysDept sysDeptRet = sysDeptService.create(sysDept);
        return Result.success(sysDeptRet);
    }

    @PutMapping
    @Operation(summary = "修改部门")
    public Result<SysDept> update(@RequestBody SysDept sysDept) {
        SysDept sysDeptRet = sysDeptService.update(sysDept);
        return Result.success(sysDeptRet);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除部门")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean bool = sysDeptService.delete(ids);
        return Result.success(bool);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取部门详情")
    public Result<SysDept> getInfo(@PathVariable("id") Integer id) {
        SysDept sysDept = sysDeptService.getById(id);
        return Result.success(sysDept);
    }

    @GetMapping("/tree")
    @Operation(summary = "获取部门树")
    public Result<List<SysDeptTreeVO>> tree() {
        List<SysDeptTreeVO> sysDeptList = sysDeptService.getTree();
        return Result.success(sysDeptList);
    }
}
