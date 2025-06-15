package com.mhua.adminbackend.pojo.vo;

import com.mhua.adminbackend.pojo.entity.Classes;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClassesVO extends Classes implements Serializable {
    private String courseName;
}
