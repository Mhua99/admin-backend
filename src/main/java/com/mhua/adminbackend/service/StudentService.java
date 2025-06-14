package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.StudentQueryDTO;
import com.mhua.adminbackend.pojo.entity.Student;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface StudentService {
    PageResult<Student> list(StudentQueryDTO studentQueryDTO);

    Student insert(Student student);

    Student update(Student student);

    Boolean delete(List<Integer> ids);

    Student getById(Integer id);
}
