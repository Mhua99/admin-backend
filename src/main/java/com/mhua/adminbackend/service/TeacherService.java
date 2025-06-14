package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.TeacherQueryDTO;
import com.mhua.adminbackend.pojo.entity.Teacher;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface TeacherService {

    PageResult<Teacher> list(TeacherQueryDTO teacherQueryDTO);

    Teacher insert(Teacher teacher);

    Teacher update(Teacher teacher);

    Boolean delete(List<Integer> ids);

    Teacher getById(Integer id);
}
