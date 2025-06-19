package com.mhua.adminbackend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mhua.adminbackend.pojo.entity.TableField;
import com.mhua.adminbackend.pojo.entity.View;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViewVO extends View implements Serializable {

    private List<TableField> fields;

    @JsonIgnore
    private LocalDateTime createTime;

    @JsonIgnore
    private LocalDateTime updateTime;

    @JsonIgnore
    private Integer createUser;

    @JsonIgnore
    private Integer updateUser;

}
