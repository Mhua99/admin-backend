package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;

import java.io.Serializable;

public class CourseQueryDTO extends PageComm implements Serializable {

    private String name;

    private Integer year;

    private Integer type;

}
