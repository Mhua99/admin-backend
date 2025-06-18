package com.mhua.adminbackend.pojo.vo;

import com.mhua.adminbackend.pojo.entity.Table;
import com.mhua.adminbackend.pojo.entity.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TableVO extends Table implements Serializable {

    private List<TableField> fields;

}
