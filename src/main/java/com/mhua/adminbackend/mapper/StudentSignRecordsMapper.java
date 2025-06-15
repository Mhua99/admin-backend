package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.StudentSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.StudentSignRecords;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface StudentSignRecordsMapper {
    Page<StudentSignRecords> list(StudentSignRecordsQueryDTO studentSignRecordsQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(StudentSignRecords studentSignRecords);

    StudentSignRecords getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(StudentSignRecords studentSignRecords);

    Boolean delete(List<Integer> ids);

    List<StudentSignRecords> existSignRecord(StudentSignRecords teacherSignRecords);
}
