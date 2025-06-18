package com.mhua.adminbackend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryMonth implements Serializable {

    /**
     * 上课次数
     */
    private Integer courseCount;

    /**
     * 课时薪水
     */
    private Integer courseSalary;

    /**
     * 基本工资
     */
    private BigDecimal miniSalary;

    /**
     * 总工资
     */
    private BigDecimal totalSalary;
}
