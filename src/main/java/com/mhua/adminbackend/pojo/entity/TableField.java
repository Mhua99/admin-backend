package com.mhua.adminbackend.pojo.entity;

import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TableField extends Comm implements Serializable {

    private String name;

    private String type;

    private String constraints;

    /**
     * 主表id
     */
    private Integer tableId;

    /**
     * 中文意思
     */
    private String meaning;

    /**
     * 排序
     */
    private Integer sort;

}
