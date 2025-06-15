package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.ClassesQueryDTO;
import com.mhua.adminbackend.pojo.entity.Classes;
import com.mhua.adminbackend.pojo.vo.ClassesVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassesMapper {

    Page<ClassesVO> list(ClassesQueryDTO classesQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(Classes classes);

    Classes getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(Classes classes);

    Boolean delete(List<Integer> ids);
}
