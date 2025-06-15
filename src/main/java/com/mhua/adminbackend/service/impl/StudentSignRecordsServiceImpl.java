package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.mapper.StudentSignRecordsMapper;
import com.mhua.adminbackend.mapper.TeacherSignRecordsMapper;
import com.mhua.adminbackend.pojo.dto.StudentSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.StudentSignRecords;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.StudentSignRecordsService;
import com.mhua.adminbackend.service.TeacherSignRecordsService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentSignRecordsServiceImpl implements StudentSignRecordsService {

    @Autowired
    private StudentSignRecordsMapper studentSignRecordsMapper;

    public PageResult<StudentSignRecords> list(StudentSignRecordsQueryDTO studentSignRecordsQueryDTO) {
        Integer page = studentSignRecordsQueryDTO.getPage();
        Integer pageSize = studentSignRecordsQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);


        Page<StudentSignRecords> pageList = studentSignRecordsMapper.list(studentSignRecordsQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public StudentSignRecords insert(StudentSignRecords teacherSignRecords) {

        List<StudentSignRecords> studentSignRecords = studentSignRecordsMapper.existSignRecord(teacherSignRecords);
        if (!studentSignRecords.isEmpty()) {
            throw new BaseException("该学生已签到");
        }

        studentSignRecordsMapper.insert(teacherSignRecords);

        return studentSignRecordsMapper.getById(teacherSignRecords.getId());
    }

    public StudentSignRecords update(StudentSignRecords studentSignRecords) {
        studentSignRecordsMapper.update(studentSignRecords);
        return studentSignRecordsMapper.getById(studentSignRecords.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return studentSignRecordsMapper.delete(ids);
    }

    public StudentSignRecords getById(Integer id) {
        return studentSignRecordsMapper.getById(id);
    }
}
