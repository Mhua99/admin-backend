package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.TeacherQueryDTO;
import com.mhua.adminbackend.pojo.entity.Teacher;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/teacher")
@Tag(name = "教师管理")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @GetMapping("/list")
    @Operation(summary = "教师列表")
    public Result<PageResult<Teacher>> list(TeacherQueryDTO teacherQueryDTO) {
        PageResult<Teacher> pageResult = teacherService.list(teacherQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增教师")
    public Result<Teacher> insert(@RequestBody Teacher teacher) {
        Teacher ret = teacherService.insert(teacher);
        return Result.success(ret);
    }

    @PutMapping
    @Operation(summary = "修改教师")
    public Result<Teacher> update(@RequestBody Teacher teacher) {
        Teacher ret = teacherService.update(teacher);
        return Result.success(ret);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除教师")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        return Result.success(teacherService.delete(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取教师详情")
    public Result<Teacher> getInfo(@PathVariable("id") Integer id) {
        return Result.success(teacherService.getById(id));
    }

}
