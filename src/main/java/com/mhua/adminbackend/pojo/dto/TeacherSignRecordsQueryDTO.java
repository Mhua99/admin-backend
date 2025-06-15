package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSignRecordsQueryDTO extends PageComm implements Serializable {

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 学生姓名
     */
    private String studentName;

    /**
     * 教练id
     */
    private Integer teacherId;

    /**
     * 教练姓名
     */
    private String teacherName;

    /**
     * 课程id
     */
    private Integer classesSignId;

    /**
     * 课程名称
     */
    private String classesSignName;

}
