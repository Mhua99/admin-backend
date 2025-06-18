package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.pojo.dto.SalaryQueryDTO;
import com.mhua.adminbackend.pojo.entity.Salary;
import com.mhua.adminbackend.pojo.vo.SalaryMonth;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface SalaryMapper {

    Page<Salary> list(SalaryQueryDTO salaryQueryDTO);

    void insert(Salary salary);

    Salary getById(Integer id);

    void update(Salary salary);

    Boolean delete(List<Integer> ids);

    SalaryMonth getCurrentTotalSalary(String salaryTime, Integer coachId);
}
