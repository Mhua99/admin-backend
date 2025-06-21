package com.mhua.adminbackend.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DicItem extends Comm implements Serializable {

    private String label;

    private String value;

    private Integer parentId;

    @JsonIgnore
    private Integer createUser;

    @JsonIgnore
    private LocalDateTime createTime;

}
