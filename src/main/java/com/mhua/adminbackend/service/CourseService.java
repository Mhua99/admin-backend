package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.CourseQueryDTO;
import com.mhua.adminbackend.pojo.entity.Course;
import com.mhua.adminbackend.pojo.vo.CourseVO;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface CourseService {
    PageResult<Course> list(CourseQueryDTO courseQueryDTO);

    Course insert(Course course);

    Course update(Course course);

    Boolean delete(List<Integer> ids);

    Course getById(Integer id);

    List<CourseVO> getAll();
}
