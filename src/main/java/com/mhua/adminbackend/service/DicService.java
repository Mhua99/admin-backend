package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.DicQueryDTO;
import com.mhua.adminbackend.pojo.entity.Dic;
import com.mhua.adminbackend.pojo.entity.DicItem;
import com.mhua.adminbackend.pojo.vo.DicVO;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface DicService {
    PageResult<Dic> list(DicQueryDTO dicQueryDTO);

    DicVO insert(DicVO dicVO);

    DicVO update(DicVO dicVO);

    Boolean delete(Integer ids);

    DicVO getById(Integer id);

    List<DicItem> getItemBySign(String sign);
}
