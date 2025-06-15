package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.ClassesQueryDTO;
import com.mhua.adminbackend.pojo.entity.Classes;
import com.mhua.adminbackend.pojo.vo.ClassesVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.ClassesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/classes")
@Tag(name = "班级管理")
public class ClassesController {

    @Autowired
    private ClassesService classesService;

    @GetMapping("/list")
    @Operation(summary = "班级列表")
    public Result<PageResult<ClassesVO>> list(ClassesQueryDTO classesQueryDTO) {
        return Result.success(classesService.list(classesQueryDTO));
    }

    @PostMapping
    @Operation(summary = "新增班级")
    public Result<Classes> insert(@RequestBody Classes classes) {
        return Result.success(classesService.insert(classes));
    }

    @PutMapping
    @Operation(summary = "修改班级")
    public Result<Classes> update(@RequestBody Classes classes) {
        return Result.success(classesService.update(classes));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除班级")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        return Result.success(classesService.delete(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取班级详情")
    public Result<Classes> getInfo(@PathVariable("id") Integer id) {
        return Result.success(classesService.getById(id));
    }

}
