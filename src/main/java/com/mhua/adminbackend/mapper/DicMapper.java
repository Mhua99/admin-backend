package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.DicQueryDTO;
import com.mhua.adminbackend.pojo.entity.Dic;
import com.mhua.adminbackend.pojo.entity.DicItem;
import com.mhua.adminbackend.pojo.vo.DicVO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Mapper
public interface DicMapper {

    Page<Dic> list(DicQueryDTO dicQueryDTO);

    @Transactional
    @AutoFill(OperationType.INSERT)
    void insert(DicVO dicVO);

    @AutoFill(OperationType.INSERT)
    void insertItem(List<DicItem> dicItem);

    DicVO getById(Integer id);

    @Transactional
    @AutoFill(OperationType.UPDATE)
    void update(DicVO dicVO);

    @Transactional
    Boolean delete(Integer id);

    int existsBySign(String sign);

    List<DicItem> getItemBySign(String sign);
}
