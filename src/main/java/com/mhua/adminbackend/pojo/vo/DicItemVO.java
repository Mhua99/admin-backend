package com.mhua.adminbackend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DicItemVO implements Serializable {

    private Integer id;

    private String label;

    private String value;

    private Integer parentId;

}
