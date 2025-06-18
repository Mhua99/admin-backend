package com.mhua.adminbackend.controller.sys;

import com.mhua.adminbackend.pojo.dto.SalaryQueryDTO;
import com.mhua.adminbackend.pojo.entity.Salary;
import com.mhua.adminbackend.pojo.vo.SalaryMonth;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.result.Result;
import com.mhua.adminbackend.service.SalaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/business/salary")
@Tag(name = "薪资管理")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @GetMapping("/list")
    @Operation(summary = "获取薪资列表")
    public Result<PageResult<Salary>> list(SalaryQueryDTO sysRolesQueryDTO) {
        PageResult<Salary> pageResult = salaryService.list(sysRolesQueryDTO);
        return Result.success(pageResult);
    }

    @PostMapping
    @Operation(summary = "新增薪资")
    public Result<Salary> insert(@RequestBody Salary salary) {
        Salary insert = salaryService.insert(salary);
        return Result.success(insert);
    }

    @PutMapping
    @Operation(summary = "修改薪资")
    public Result<Salary> update(@RequestBody Salary salary) {
        Salary update = salaryService.update(salary);
        return Result.success(update);
    }

    @DeleteMapping("/{ids}")
    @Operation(summary = "删除薪资")
    public Result<Boolean> delete(@PathVariable("ids") List<Integer> ids) {
        Boolean ret = salaryService.delete(ids);
        return Result.success(ret);
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取薪资详情")
    public Result<Salary> getInfo(@PathVariable("id") Integer id) {
        Salary salary = salaryService.getById(id);
        return Result.success(salary);
    }

    @GetMapping("/getCurrentTotalSalary")
    @Operation(summary = "获取月份薪资")
    public Result<SalaryMonth> getTotalSalary(@RequestParam @DateTimeFormat(pattern = "yyyy-MM") LocalDate salaryTime, @RequestParam Integer coachId) {
        return Result.success(salaryService.getCurrentTotalSalary(salaryTime, coachId));
    }

}
