package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.StudentSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.StudentSignRecords;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface StudentSignRecordsService {
    PageResult<StudentSignRecords> list(StudentSignRecordsQueryDTO studentSignRecordsQueryDTO);

    StudentSignRecords insert(StudentSignRecords  studentSignRecords);

    StudentSignRecords update(StudentSignRecords studentSignRecords);

    Boolean delete(List<Integer> ids);

    StudentSignRecords getById(Integer id);
}
