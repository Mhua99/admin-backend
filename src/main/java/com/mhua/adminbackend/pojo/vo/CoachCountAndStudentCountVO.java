package com.mhua.adminbackend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CoachCountAndStudentCountVO implements Serializable {

    private Integer coachCount;

    private Integer studentCount;
}
