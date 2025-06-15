package com.mhua.adminbackend.pojo.vo;

import com.mhua.adminbackend.pojo.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseVO extends Course implements Serializable {

    private String showName;

}
