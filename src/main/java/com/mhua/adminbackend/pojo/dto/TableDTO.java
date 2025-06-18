package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;
import com.mhua.adminbackend.pojo.entity.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableDTO extends PageComm implements Serializable {

    private Integer id;

    private String name;

    private String desc;

    private List<TableField> fields;

}