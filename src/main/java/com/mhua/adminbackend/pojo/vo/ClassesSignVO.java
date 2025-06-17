package com.mhua.adminbackend.pojo.vo;

import com.mhua.adminbackend.pojo.entity.ClassesSign;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesSignVO implements Serializable {

    private Integer id;

    /**
     * 费用
     */
    private BigDecimal cost;

    /**
     * 课程数
     */
    private Integer courseCount;

    /**
     * 学生id
     */
    private Integer  studentId;

    /**
     * 已用课程数
     */
    private Integer usedCount;

    /**
     * 未用课程数
     */
    private Integer useCount;
}
