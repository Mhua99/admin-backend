package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.StudentSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.StudentSignRecords;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.StudentSignRecordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/studentSignRecords")
@Tag(name = "课程学习记录管理")
public class StudentSignRecordsController {

    @Autowired
    private StudentSignRecordsService studentSignRecordsService;

    @GetMapping("/list")
    @Operation(summary = "课程学习记录列表")
    public Result<PageResult<StudentSignRecords>> list(StudentSignRecordsQueryDTO studentSignRecordsQueryDTO) {
        PageResult<StudentSignRecords> pageResult = studentSignRecordsService.list(studentSignRecordsQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增课程学习记录")
    public Result<StudentSignRecords> insert(@RequestBody StudentSignRecords studentSignRecords) {
        StudentSignRecords insert = studentSignRecordsService.insert(studentSignRecords);
        return Result.success(insert);
    }

    @PutMapping
    @Operation(summary = "修改课程学习记录")
    public Result<StudentSignRecords> update(@RequestBody StudentSignRecords studentSignRecords) {
        StudentSignRecords update = studentSignRecordsService.update(studentSignRecords);
        return Result.success(update);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除课程学习记录")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean delete = studentSignRecordsService.delete(ids);
        return Result.success(delete);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程学习记录详情")
    public Result<StudentSignRecords> getInfo(@PathVariable("id") Integer id) {
        StudentSignRecords info = studentSignRecordsService.getById(id);
        return Result.success(info);
    }
}
