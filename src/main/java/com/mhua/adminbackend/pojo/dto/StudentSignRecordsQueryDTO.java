package com.mhua.adminbackend.pojo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhua.adminbackend.pojo.comm.PageComm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentSignRecordsQueryDTO extends PageComm implements Serializable {

    /**
     * 学生id
     */
    private Integer studentId;

    /**
     * 教练签到id
     */
    private Integer teacherSignId;

    /**
     * 签到时间
     */
    @DateTimeFormat(pattern = "yyyy-M-d HH:mm:ss")
    @JsonFormat(pattern = "yyyy-M-d HH:mm:ss")
    private LocalDateTime signTime;

}
