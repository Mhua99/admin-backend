package com.mhua.adminbackend.pojo.vo;

import com.mhua.adminbackend.pojo.entity.SysMenu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysMenuTreeVO extends SysMenu implements Serializable {

    private List<SysMenuTreeVO> children;

}