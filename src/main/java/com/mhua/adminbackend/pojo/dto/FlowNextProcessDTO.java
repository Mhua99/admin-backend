package com.mhua.adminbackend.pojo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FlowNextProcessDTO implements Serializable {

    private String processDefinitionId;

    private String currentNodeId;
}
