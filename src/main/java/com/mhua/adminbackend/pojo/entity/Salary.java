package com.mhua.adminbackend.pojo.entity;

import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary extends Comm implements Serializable {

    private Integer coachId;

    private BigDecimal salary;

    private String salaryTime;

    private String remarks;
}
