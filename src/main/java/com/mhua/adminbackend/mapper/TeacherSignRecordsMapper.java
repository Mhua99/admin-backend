package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TeacherSignRecordsMapper {
    Page<TeacherSignRecords> list(TeacherSignRecordsQueryDTO teacherSignRecordsQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(TeacherSignRecords teacherSignRecords);

    TeacherSignRecords getById(Integer id);

    @AutoFill(OperationType.UPDATE)
    void update(TeacherSignRecords teacherSignRecords);

    Boolean delete(List<Integer> ids);
}
