package com.mhua.adminbackend.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProcessModal implements Serializable {
    private String id;

    private String name;

    private String processDefinitionKey;

    private int version;

    private String bpmnXml;

    private String deploymentId;
}
