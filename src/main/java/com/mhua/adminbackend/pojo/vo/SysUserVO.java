package com.mhua.adminbackend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mhua.adminbackend.pojo.entity.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserVO extends SysUser implements Serializable {

    @JsonIgnore
    private String password;
}
