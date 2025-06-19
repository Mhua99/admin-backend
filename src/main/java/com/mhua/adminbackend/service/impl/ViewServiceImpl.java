package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.TableMapper;
import com.mhua.adminbackend.mapper.ViewMapper;
import com.mhua.adminbackend.pojo.dto.ViewDTO;
import com.mhua.adminbackend.pojo.entity.TableField;
import com.mhua.adminbackend.pojo.entity.View;
import com.mhua.adminbackend.pojo.vo.ViewVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.ViewService;
import com.mhua.adminbackend.utils.NumberUtils;
import com.mhua.adminbackend.utils.RandomChart;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ViewServiceImpl implements ViewService {

    @Autowired
    private ViewMapper viewMapper;

    @Autowired
    private TableMapper tableMapper;


    public PageResult<View> list(ViewDTO viewDTO) {
        Integer page = viewDTO.getPage();
        Integer pageSize = viewDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);


        Page<View> pageList = viewMapper.list(viewDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public View insert(View view) {
        view.setUrl(RandomChart.generateRandomLetters(10));
        viewMapper.insert(view);

        return viewMapper.getById(view.getId());
    }

    public ViewVO getById(Integer id) {
        View view = viewMapper.getById(id);
        List<TableField> fieldsByTableId;

        ViewVO viewVO = new ViewVO();

        String config = view.getConfig();
        if(config == null || config.trim().isEmpty()) {
            fieldsByTableId = tableMapper.findFieldsByTableId(view.getTableId());
            BeanUtils.copyProperties(view, viewVO);
            viewVO.setFields(fieldsByTableId);
        }

        return viewVO;
    }

    public View update(View view) {
        viewMapper.update(view);
        return viewMapper.getById(view.getId());
    }
}
