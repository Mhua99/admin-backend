package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.CourseMapper;
import com.mhua.adminbackend.pojo.dto.CourseQueryDTO;
import com.mhua.adminbackend.pojo.entity.Course;
import com.mhua.adminbackend.pojo.vo.CourseVO;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.CourseService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseMapper courseMapper;

    public PageResult<Course> list(CourseQueryDTO courseQueryDTO) {
        Integer page = courseQueryDTO.getPage();
        Integer pageSize = courseQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);


        Page<Course> pageList = courseMapper.list(courseQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public Course insert(Course course) {
        courseMapper.insert(course);

        return courseMapper.getById(course.getId());
    }

    public Course update(Course course) {
        courseMapper.update(course);
        return courseMapper.getById(course.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return  courseMapper.delete(ids);
    }

    public Course getById(Integer id) {
        return courseMapper.getById(id);
    }

    @Override
    public List<CourseVO> getAll() {
        List<Course> list = courseMapper.getAll();

        Map<Integer, String> semesterMap = new HashMap<>();
        semesterMap.put(1, "上学期");
        semesterMap.put(2, "下学期");
        semesterMap.put(3, "暑假班");
        semesterMap.put(4, "寒假班");

        return list.stream().map(course -> {
            CourseVO courseVO = new CourseVO(semesterMap.get(course.getType()) + '-' + course.getName());
            BeanUtils.copyProperties(course, courseVO);
            return courseVO;
        }).toList();
    }
}
