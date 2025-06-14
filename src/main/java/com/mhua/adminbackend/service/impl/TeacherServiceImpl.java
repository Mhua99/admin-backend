package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.TeacherMapper;
import com.mhua.adminbackend.pojo.dto.TeacherQueryDTO;
import com.mhua.adminbackend.pojo.entity.Teacher;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherMapper teacherMapper;

    public PageResult<Teacher> list(TeacherQueryDTO teacherQueryDTO) {
        int page = teacherQueryDTO.getPage() == null ? 1 : teacherQueryDTO.getPage();
        int pageSize = teacherQueryDTO.getPageSize() == null ? 10 : teacherQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);

        Page<Teacher> pageList = teacherMapper.list(teacherQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public Teacher insert(Teacher teacher) {
        teacherMapper.insert(teacher);

        return teacherMapper.getById(teacher.getId());
    }

    public Teacher update(Teacher teacher) {
        teacherMapper.update(teacher);
        return teacherMapper.getById(teacher.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return teacherMapper.delete(ids);
    }

    public Teacher getById(Integer id) {
        return teacherMapper.getById(id);
    }
}
