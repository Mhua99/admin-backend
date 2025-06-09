package com.mhua.adminbackend.pojo.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.mhua.adminbackend.pojo.entity.SysDept;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysDeptTreeVO extends SysDept implements Serializable {

    private List<SysDeptTreeVO> children;

}