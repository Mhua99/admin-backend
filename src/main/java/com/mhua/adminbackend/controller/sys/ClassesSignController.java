package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.ClassesSignQueryDTO;
import com.mhua.adminbackend.pojo.entity.ClassesSign;
import com.mhua.adminbackend.pojo.vo.ClassesSignVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.ClassesSignService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/classesSign")
@Tag(name = "学生课程报名管理")
public class ClassesSignController {

    @Autowired
    private ClassesSignService classesSignService;

    @GetMapping("/list")
    @Operation(summary = "学生课程报名列表")
    public Result<PageResult<ClassesSignVO>> list(ClassesSignQueryDTO classesSignQueryDTO) {
        PageResult<ClassesSignVO> pageResult = classesSignService.list(classesSignQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增学生课程报名")
    public Result<ClassesSign> insert(@RequestBody ClassesSign classesSign) {
        ClassesSign ret = classesSignService.insert(classesSign);
        return Result.success(ret);
    }

    @PutMapping
    @Operation(summary = "修改学生课程报名")
    public Result<ClassesSign> update(@RequestBody ClassesSign classesSign) {
        ClassesSign ret = classesSignService.update(classesSign);
        return Result.success(ret);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除学生课程报名")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean ret = classesSignService.delete(ids);
        return Result.success(ret);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取学生课程报名详情")
    public Result<ClassesSign> getInfo(@PathVariable("id") Integer id) {
        ClassesSign ret = classesSignService.getById(id);
        return Result.success(ret);
    }

}
