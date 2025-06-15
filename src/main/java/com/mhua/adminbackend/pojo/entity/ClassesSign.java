package com.mhua.adminbackend.pojo.entity;

import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClassesSign extends Comm implements Serializable {

    private Integer userId;

    private Integer classesId;

}
