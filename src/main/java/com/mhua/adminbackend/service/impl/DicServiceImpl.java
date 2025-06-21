package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.mapper.DicMapper;
import com.mhua.adminbackend.pojo.dto.DicQueryDTO;
import com.mhua.adminbackend.pojo.entity.Dic;
import com.mhua.adminbackend.pojo.entity.DicItem;
import com.mhua.adminbackend.pojo.vo.DicVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.DicService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DicServiceImpl implements DicService {

    @Autowired
    private DicMapper dicMapper;

    public PageResult<Dic> list(DicQueryDTO dicQueryDTO) {
        Integer page = dicQueryDTO.getPage();
        Integer pageSize = dicQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<Dic> pageList = dicMapper.list(dicQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    @Transactional
    public DicVO insert(DicVO dicVO) {

        if (dicMapper.existsBySign(dicVO.getSign()) > 0) {
            throw new BaseException("字典标识已存在");
        }

        try {
            dicMapper.insert(dicVO);
        } catch (Exception e) {
            throw new BaseException("字典标识已存在");
        }

        // 给每条子表数据添加 parentId
        Integer parentId = dicVO.getId();

        if (dicVO.getDicItemList() != null && !dicVO.getDicItemList().isEmpty()) {
            dicVO.getDicItemList().forEach(item -> item.setParentId(parentId));
        }

        dicMapper.insertItem(dicVO.getDicItemList());
        return dicMapper.getById(dicVO.getId());
    }

    public DicVO update(DicVO dicVO) {
        dicMapper.update(dicVO);
        return dicMapper.getById(dicVO.getId());
    }

    public Boolean delete(Integer id) {
        return dicMapper.delete(id);
    }

    public DicVO getById(Integer id) {
        return dicMapper.getById(id);
    }

    public List<DicItem> getItemBySign(String sign) {
        return dicMapper.getItemBySign(sign);
    }
}
