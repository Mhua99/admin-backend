package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface TeacherSignRecordsService {
    PageResult<TeacherSignRecords> list(TeacherSignRecordsQueryDTO teacherSignRecordsQueryDTO);

    TeacherSignRecords insert(TeacherSignRecords teacherSignRecords);

    TeacherSignRecords update(TeacherSignRecords teacherSignRecords);

    Boolean delete(List<Integer> ids);

    TeacherSignRecords getById(Integer id);
}
