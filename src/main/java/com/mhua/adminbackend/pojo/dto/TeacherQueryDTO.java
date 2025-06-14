package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TeacherQueryDTO extends PageComm implements Serializable {

    private String name;

    private String level;

    private String sex;

}
