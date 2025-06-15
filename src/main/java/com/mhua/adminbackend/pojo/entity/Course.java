package com.mhua.adminbackend.pojo.entity;

import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Course extends Comm implements Serializable {

    /**
     * 课程名称
     */
    private String name;

    /**
     *  类型
     */
    private Integer type;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 课时数
     */
    private Integer courseCount;

}
