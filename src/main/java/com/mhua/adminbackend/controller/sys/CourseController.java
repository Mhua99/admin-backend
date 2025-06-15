package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.CourseQueryDTO;
import com.mhua.adminbackend.pojo.entity.Course;
import com.mhua.adminbackend.pojo.vo.CourseVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/business/course")
@Tag(name = "课程管理")
public class CourseController {

    @Autowired
    private CourseService courseService;

    @GetMapping("/list")
    @Operation(summary = "课程列表")
    public Result<PageResult<Course>> list(CourseQueryDTO  courseQueryDTO ) {
        PageResult<Course> pageResult = courseService.list(courseQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增课程")
    public Result<Course> insert(@RequestBody Course course) {
        return Result.success(courseService.insert(course));
    }

    @PutMapping
    @Operation(summary = "修改课程")
    public Result<Course> update(@RequestBody Course course) {
        return Result.success(courseService.update(course));
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除课程")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        return Result.success(courseService.delete(ids));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取课程详情")
    public Result<Course> getInfo(@PathVariable("id") Integer id) {
        return Result.success(courseService.getById(id));
    }

    @GetMapping("/all")
    @Operation(summary = "获取所有课程")
    public Result<List<CourseVO>> getAll() {
        return Result.success(courseService.getAll());
    }
}
