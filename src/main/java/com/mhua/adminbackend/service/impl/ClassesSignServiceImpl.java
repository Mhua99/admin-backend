package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.ClassesMapper;
import com.mhua.adminbackend.mapper.ClassesSignMapper;
import com.mhua.adminbackend.pojo.dto.ClassesSignQueryDTO;
import com.mhua.adminbackend.pojo.entity.ClassesSign;
import com.mhua.adminbackend.pojo.vo.ClassesSignVO;
import com.mhua.adminbackend.pojo.vo.ClassesVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.ClassesSignService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesSignServiceImpl implements ClassesSignService {

    @Autowired
    private ClassesSignMapper classesSignMapper;

    public PageResult<ClassesSignVO> list(ClassesSignQueryDTO classesSignQueryDTO) {
        Integer page = classesSignQueryDTO.getPage();
        Integer pageSize = classesSignQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<ClassesSignVO> pageList = classesSignMapper.list(classesSignQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public ClassesSign insert(ClassesSign classesSign) {
        classesSignMapper.insert(classesSign);

        return classesSignMapper.getById(classesSign.getId());
    }

    public ClassesSign update(ClassesSign classesSign) {
        classesSignMapper.update(classesSign);
        return classesSignMapper.getById(classesSign.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return classesSignMapper.delete(ids);
    }

    public ClassesSign getById(Integer id) {
        return classesSignMapper.getById(id);
    }
}
