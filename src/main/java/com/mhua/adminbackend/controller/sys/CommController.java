package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.CommService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/system/comm")
@Tag(name = "通用管理")
public class CommController {

    @Autowired
    private CommService commService;

    @GetMapping("/{changePathSign}/list")
    @Operation(summary = "通用列表")
    public Result<PageResult<Map<String, Object>>> list(@PathVariable("changePathSign") String sign, @RequestParam Map<String, Object> params) {

        PageResult<Map<String, Object>> pageResult = commService.list(sign, params);

        return Result.success(pageResult);
    }

    @PostMapping("/{changePathSign}")
    @Operation(summary = "通用新增")
    public Result<Map<String, Object>> insert(@PathVariable("changePathSign") String sign, @RequestBody Map<String, Object> params) {
        Map<String, Object> object = commService.insert(sign, params);
        return Result.success(object);
    }

    @PutMapping("/{changePathSign}")
    @Operation(summary = "通用修改")
    public Result<Map<String, Object>> update(@PathVariable("changePathSign") String sign, @RequestBody Map<String, Object> params) {
        Map<String, Object> object = commService.update(sign, params);
        return Result.success(object);
    }

    @DeleteMapping("/{changePathSign}/{id}")
    @Operation(summary = "通用删除")
    public Result<Boolean> delete(@PathVariable("changePathSign") String sign, @PathVariable("id") Integer id) {
        Boolean ret = commService.delete(sign, id);
        return Result.success(ret);
    }

    @GetMapping("/{changePathSign}/getTableField")
    @Operation(summary = "获取表格字段")
    public Result<Object> getAll(@PathVariable("changePathSign") String sign) {
        Object object = commService.getTableField(sign);
        return Result.success(object);
    }
}
