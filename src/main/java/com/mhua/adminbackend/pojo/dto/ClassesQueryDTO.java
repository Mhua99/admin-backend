package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassesQueryDTO extends PageComm implements Serializable {
    /**
     * 课程 id
     */
    private Integer courseId;

    /**
     * 学年
     */
    private Integer year;
}
