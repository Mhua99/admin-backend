package com.mhua.adminbackend.pojo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser extends Comm implements Serializable {

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Integer deptId;

    private String avatar;

    private String password;
}
