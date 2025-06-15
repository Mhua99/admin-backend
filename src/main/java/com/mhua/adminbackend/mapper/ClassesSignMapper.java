package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.ClassesSignQueryDTO;
import com.mhua.adminbackend.pojo.entity.ClassesSign;
import com.mhua.adminbackend.pojo.vo.ClassesSignVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassesSignMapper {
    Page<ClassesSignVO> list(ClassesSignQueryDTO classesSignQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(ClassesSign classesSign);

    ClassesSign getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(ClassesSign classesSign);

    Boolean delete(List<Integer> ids);
}
