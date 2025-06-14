package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.StudentMapper;
import com.mhua.adminbackend.pojo.dto.StudentQueryDTO;
import com.mhua.adminbackend.pojo.entity.Student;
import com.mhua.adminbackend.pojo.entity.SysDept;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.StudentService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    public PageResult<Student> list(StudentQueryDTO studentQueryDTO) {
        Integer page = studentQueryDTO.getPage();
        Integer pageSize = studentQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);

        Page<Student> pageList = studentMapper.list(studentQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }


    public Student insert(Student student) {
        studentMapper.insert(student);
        return studentMapper.getById(student.getId());
    }

    public Student update(Student student) {
        studentMapper.update(student);
        return studentMapper.getById(student.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return studentMapper.delete(ids);
    }

    public Student getById(Integer id) {
        return studentMapper.getById(id);
    }
}
