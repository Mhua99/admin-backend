package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.ViewDTO;
import com.mhua.adminbackend.pojo.entity.View;
import com.mhua.adminbackend.pojo.vo.ViewVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.ViewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sys/view")
@Tag(name = "视图管理")
public class ViewController {

    @Autowired
    private ViewService viewService;

    @GetMapping("/list")
    @Operation(summary = "获取视图列表")
    public Result<PageResult<View>> list(ViewDTO viewDTO) {

        PageResult<View> pageResult =viewService.list(viewDTO);

        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增视图")
    public Result<View> insert(View view) {
        View viewRet = viewService.insert(view);
        return Result.success(viewRet);
    }

    @PutMapping
    @Operation(summary = "修改视图")
    public Result<View> update(@RequestBody View view) {
        View viewRet = viewService.update(view);
        return Result.success(viewRet);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取视图")
    public Result<ViewVO> getById(@PathVariable("id") Integer id) {
        ViewVO viewVO = viewService.getById(id);
        return Result.success(viewVO);
    }
}
