package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.TeacherQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysUser;
import com.mhua.adminbackend.pojo.entity.Teacher;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherMapper {
    Page<Teacher> list(TeacherQueryDTO teacherQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(Teacher teacher);

    Teacher getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(Teacher teacher);

    Boolean delete(List<Integer> ids);
}
