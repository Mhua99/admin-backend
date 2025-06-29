package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessDefinitionDTO extends PageComm implements Serializable {
    private Integer id;

    private String processDefinitionKey;
}
