package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.ViewDTO;
import com.mhua.adminbackend.pojo.entity.View;
import com.mhua.adminbackend.pojo.vo.ViewAllVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ViewMapper {
    Page<View> list(ViewDTO viewDTO);

    @AutoFill(OperationType.INSERT)
    void insert(View view);

    View getById(Integer id);

    void update(View view);

    View getBySign(String sign);

    Boolean delete(List<Integer> ids);

    List<ViewAllVO> getAll();
}
