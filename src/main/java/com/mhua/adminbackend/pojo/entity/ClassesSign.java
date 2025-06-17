package com.mhua.adminbackend.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassesSign extends Comm implements Serializable {

    private Integer studentId;

    private Integer classesId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate startTime;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate endTime;

}
