package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.ViewDTO;
import com.mhua.adminbackend.pojo.entity.View;
import com.mhua.adminbackend.pojo.vo.ViewVO;
import com.mhua.adminbackend.result.PageResult;

public interface ViewService {
    PageResult<View> list(ViewDTO viewDTO);

    View insert(View view);

    ViewVO getById(Integer id);

    View update(View view);
}
