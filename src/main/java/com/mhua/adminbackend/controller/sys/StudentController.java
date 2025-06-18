package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.StudentQueryDTO;
import com.mhua.adminbackend.pojo.entity.Student;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/student")
@Tag(name = "学生管理")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    @Operation(summary = "学生列表")
    public Result<PageResult<Student>> list(StudentQueryDTO studentQueryDTO) {
        PageResult<Student> ret =  studentService.list(studentQueryDTO);
        return Result.success(ret);
    }

    @PostMapping
    @Operation(summary = "新增学生")
    public Result<Student> insert(@RequestBody Student student) {
        Student ret = studentService.insert(student);
        return Result.success(ret);
    }

    @PutMapping
    @Operation(summary = "修改学生")
    public Result<Student> update(@RequestBody Student student) {
        Student ret = studentService.update(student);
        return Result.success(ret);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除学生")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean ret = studentService.delete(ids);
        return Result.success(ret);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取学生详情")
    public Result<Student> getInfo(@PathVariable("id") Integer id) {
        Student ret = studentService.getById(id);
        return Result.success(ret);
    }

}
