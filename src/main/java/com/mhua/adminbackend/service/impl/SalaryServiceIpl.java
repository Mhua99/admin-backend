package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.SalaryMapper;
import com.mhua.adminbackend.pojo.dto.SalaryQueryDTO;
import com.mhua.adminbackend.pojo.entity.Salary;
import com.mhua.adminbackend.pojo.entity.Student;
import com.mhua.adminbackend.pojo.vo.SalaryMonth;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.SalaryService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class SalaryServiceIpl implements SalaryService {

    @Autowired
    private SalaryMapper salaryMapper;

    public PageResult<Salary> list(SalaryQueryDTO salaryQueryDTO) {

        Integer page = salaryQueryDTO.getPage();
        Integer pageSize = salaryQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<Salary> pageList = salaryMapper.list(salaryQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public Salary insert(Salary salary) {
        salaryMapper.insert(salary);

        return salaryMapper.getById(salary.getId());
    }

    public Salary update(Salary salary) {
        salaryMapper.update(salary);
        return salaryMapper.getById(salary.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return salaryMapper.delete(ids);
    }

    public Salary getById(Integer id) {
        return salaryMapper.getById(id);
    }

    public SalaryMonth getCurrentTotalSalary(LocalDate salaryTime, Integer coachId) {
        String month = salaryTime.format(DateTimeFormatter.ofPattern("yyyy-MM"));
        return salaryMapper.getCurrentTotalSalary(month, coachId);
    }
}
