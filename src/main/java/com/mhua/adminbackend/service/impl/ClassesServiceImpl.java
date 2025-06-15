package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.ClassesMapper;
import com.mhua.adminbackend.pojo.dto.ClassesQueryDTO;
import com.mhua.adminbackend.pojo.entity.Classes;
import com.mhua.adminbackend.pojo.entity.Course;
import com.mhua.adminbackend.pojo.vo.ClassesVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.ClassesService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassesServiceImpl implements ClassesService {

    @Autowired
    private ClassesMapper classesMapper;

    public PageResult<ClassesVO> list(ClassesQueryDTO classesQueryDTO) {
        Integer page = classesQueryDTO.getPage();
        Integer pageSize = classesQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);


        Page<ClassesVO> pageList = classesMapper.list(classesQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public Classes insert(Classes classes) {
        classesMapper.insert(classes);
        return classesMapper.getById(classes.getId());
    }

    public Classes update(Classes classes) {
        classesMapper.update(classes);
        return classesMapper.getById(classes.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return classesMapper.delete(ids);
    }

    public Classes getById(Integer id) {
        return classesMapper.getById(id);
    }
}
