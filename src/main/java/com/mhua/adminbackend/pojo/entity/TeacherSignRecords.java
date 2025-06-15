package com.mhua.adminbackend.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TeacherSignRecords extends Comm implements Serializable {

    /**
     * 教练id
     */
    private Integer teacherId;

    /**
     * 教练姓名
     */
    private String teacherName;

    /**
     * 签到时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime signTime;

}
