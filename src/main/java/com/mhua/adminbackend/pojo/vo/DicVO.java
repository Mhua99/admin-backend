package com.mhua.adminbackend.pojo.vo;

import com.mhua.adminbackend.pojo.entity.Dic;
import com.mhua.adminbackend.pojo.entity.DicItem;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DicVO extends Dic implements Serializable {

    private List<DicItem> dicItemList;

}
