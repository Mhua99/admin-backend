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
public class ClassesSignVO extends ClassesSign implements Serializable {

    private BigDecimal cost;

    private Integer courseCount;
}
