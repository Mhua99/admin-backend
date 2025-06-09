package com.mhua.adminbackend.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUserQueryDTO implements Serializable {

    private String username;

    private String nickname;

    private String email;

    private String phone;

    private Integer deptId;

    private Integer page;

    private Integer pageSize;

}
