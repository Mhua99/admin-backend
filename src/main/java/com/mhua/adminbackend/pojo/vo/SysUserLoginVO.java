package com.mhua.adminbackend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysUserLoginVO {
    private String token;
    private Integer userId;
}
