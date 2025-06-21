package com.mhua.adminbackend.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ViewAllVO implements Serializable {

    private Integer id;

    private String url;

    private String sign;

    private String tableName;

    private String desc;

    private String tableId;

}
