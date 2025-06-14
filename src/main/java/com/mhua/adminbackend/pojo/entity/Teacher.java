package com.mhua.adminbackend.pojo.entity;

import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Teacher extends Comm implements Serializable {

    private String name;

    private String level;

    private BigDecimal miniSalary;

    private BigDecimal courseSalary;

    private Integer age;

    private String sex;

}
