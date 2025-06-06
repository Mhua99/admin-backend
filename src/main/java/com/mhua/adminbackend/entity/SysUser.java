package com.mhua.adminbackend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SysUser implements Serializable {

    private Integer id;

    private String username;

    private String nickname;

    private Integer email;

    private Integer phone;

    private Integer deptId;

    private String avatar;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private Integer createUser;

    private Integer updateUser;
}
