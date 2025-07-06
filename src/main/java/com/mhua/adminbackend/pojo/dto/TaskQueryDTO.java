package com.mhua.adminbackend.pojo.dto;

import com.mhua.adminbackend.pojo.comm.PageComm;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
public class TaskQueryDTO extends PageComm implements Serializable {
    private String type;

    private String taskId;

    private String processDefinitionKey;

    private String historyId;
}
