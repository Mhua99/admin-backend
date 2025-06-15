package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.CourseQueryDTO;
import com.mhua.adminbackend.pojo.entity.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CourseMapper {
    Page<Course> list(CourseQueryDTO courseQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(Course course);

    Course getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(Course course);

    Boolean delete(List<Integer> ids);

    List<Course> getAll();
}
