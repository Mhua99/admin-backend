package com.mhua.adminbackend.pojo.entity;

import com.mhua.adminbackend.pojo.comm.Comm;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SysMenu extends Comm implements Serializable {

    private Integer parentId;

    private String name;

    private String url;

    /**
     * 1 菜单
     * 2 页面
     */
    private Integer type;

    private String icon;

    private String parentName;

    private Integer sort;

}
