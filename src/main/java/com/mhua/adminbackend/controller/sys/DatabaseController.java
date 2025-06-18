package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.TableDTO;
import com.mhua.adminbackend.pojo.entity.Table;
import com.mhua.adminbackend.pojo.vo.TableVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.DatabaseService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/database")
@Tag(name = "数据库管理")
public class DatabaseController {

    @Autowired
    private DatabaseService databaseService;

    @GetMapping("/list")
    @Operation(summary = "数据库列表")
    public Result<PageResult<TableVO>> list(TableDTO tableDTO) {
        PageResult<TableVO> pageResult = databaseService.list(tableDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "创建数据库表")
    public Result createTable(@RequestBody TableDTO tableDTO) {
        databaseService.insert(tableDTO);
        return Result.success("创建成功");
    }

    @PutMapping
    @Operation(summary = "修改数据库表")
    public Result updateTable(@RequestBody TableDTO tableDTO) {
        databaseService.update(tableDTO);
        return Result.success("修改成功");
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除数据库表")
    public Result<Boolean> deleteTable(@PathVariable("ids") Integer id) {
        return Result.success(databaseService.delete(id));
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取数据库表详情")
    public Result<TableVO> getTable(@PathVariable("id") Integer id) {
        return Result.success(databaseService.findTableAndFields(id));
    }
}
