package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.StudentQueryDTO;
import com.mhua.adminbackend.pojo.entity.Student;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentMapper {
    Page<Student> list(StudentQueryDTO studentQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(Student student);

    Student getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(Student student);

    Boolean delete(List<Integer> ids);
}
