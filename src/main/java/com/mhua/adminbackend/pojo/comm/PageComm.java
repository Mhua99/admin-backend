package com.mhua.adminbackend.pojo.comm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageComm {

    private Integer page;

    private Integer pageSize;
}
