package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.ClassesSignQueryDTO;
import com.mhua.adminbackend.pojo.entity.ClassesSign;
import com.mhua.adminbackend.pojo.vo.ClassesSignVO;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface ClassesSignService {

    PageResult<ClassesSignVO> list(ClassesSignQueryDTO classesSignQueryDTO);

    ClassesSign insert(ClassesSign classesSign);

    ClassesSign update(ClassesSign classesSign);

    Boolean delete(List<Integer> ids);

    ClassesSign getById(Integer id);
}
