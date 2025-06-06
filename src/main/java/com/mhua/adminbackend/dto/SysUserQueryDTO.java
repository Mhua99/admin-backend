package com.mhua.adminbackend.dto;

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
public class SysUserQueryDTO implements Serializable {

    private String username;

    private String nickname;

    private Integer email;

    private Integer phone;

    private Integer deptId;

}
