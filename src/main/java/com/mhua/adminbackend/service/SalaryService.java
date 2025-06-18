package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.SalaryQueryDTO;
import com.mhua.adminbackend.pojo.entity.Salary;
import com.mhua.adminbackend.pojo.vo.SalaryMonth;
import com.mhua.adminbackend.result.PageResult;

import java.time.LocalDate;
import java.util.List;

public interface SalaryService {

    PageResult<Salary> list(SalaryQueryDTO sysRolesQueryDTO);

    Salary insert(Salary salary);

    Salary update(Salary salary);

    Boolean delete(List<Integer> ids);

    Salary getById(Integer id);

    SalaryMonth getCurrentTotalSalary(LocalDate salaryTime, Integer coachId);
}
