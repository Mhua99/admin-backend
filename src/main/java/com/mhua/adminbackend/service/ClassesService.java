package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.ClassesQueryDTO;
import com.mhua.adminbackend.pojo.entity.Classes;
import com.mhua.adminbackend.pojo.vo.ClassesVO;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface ClassesService {
    PageResult<ClassesVO> list(ClassesQueryDTO classesQueryDTO);

    Classes insert(Classes classes);

    Classes update(Classes classes);

    Boolean delete(List<Integer> ids);

    Classes getById(Integer id);
}
