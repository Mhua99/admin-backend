package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.TeacherSignRecordsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/teacherSignRecords")
@Tag(name = "课程学习记录管理")
public class TeacherSignRecordsController {

    @Autowired
    private TeacherSignRecordsService teacherSignRecordsService;

    @GetMapping("/list")
    @Operation(summary = "课程学习记录列表")
    public Result<PageResult<TeacherSignRecords>> list(TeacherSignRecordsQueryDTO teacherSignRecordsQueryDTO) {
        PageResult<TeacherSignRecords> pageResult = teacherSignRecordsService.list(teacherSignRecordsQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增课程学习记录")
    public Result<TeacherSignRecords> insert(@RequestBody TeacherSignRecords teacherSignRecords) {
        TeacherSignRecords insert = teacherSignRecordsService.insert(teacherSignRecords);
        return Result.success(insert);
    }

    @PutMapping
    @Operation(summary = "修改课程学习记录")
    public Result<TeacherSignRecords> update(@RequestBody TeacherSignRecords teacherSignRecords) {
        TeacherSignRecords update = teacherSignRecordsService.update(teacherSignRecords);
        return Result.success(update);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除课程学习记录")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean delete = teacherSignRecordsService.delete(ids);
        return Result.success(delete);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程学习记录详情")
    public Result<TeacherSignRecords> getInfo(@PathVariable("id") Integer id) {
        TeacherSignRecords info = teacherSignRecordsService.getById(id);
        return Result.success(info);
    }
}
