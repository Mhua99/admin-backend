package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.TeacherSignRecordsMapper;
import com.mhua.adminbackend.pojo.dto.TeacherSignRecordsQueryDTO;
import com.mhua.adminbackend.pojo.entity.TeacherSignRecords;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.TeacherSignRecordsService;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherSignRecordsServiceImpl implements TeacherSignRecordsService {

    @Autowired
    private TeacherSignRecordsMapper teacherSignRecordsMapper;

    public PageResult<TeacherSignRecords> list(TeacherSignRecordsQueryDTO teacherSignRecordsQueryDTO) {
        Integer page = teacherSignRecordsQueryDTO.getPage();
        Integer pageSize = teacherSignRecordsQueryDTO.getPageSize();
        page = NumberUtils.checkInteger(page, 1);
        pageSize = NumberUtils.checkInteger(pageSize, 10);

        PageHelper.startPage(page, pageSize);


        Page<TeacherSignRecords> pageList = teacherSignRecordsMapper.list(teacherSignRecordsQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    public TeacherSignRecords insert(TeacherSignRecords teacherSignRecords) {
        teacherSignRecordsMapper.insert(teacherSignRecords);

        return teacherSignRecordsMapper.getById(teacherSignRecords.getId());
    }

    public TeacherSignRecords update(TeacherSignRecords teacherSignRecords) {
        teacherSignRecordsMapper.update(teacherSignRecords);
        return teacherSignRecordsMapper.getById(teacherSignRecords.getId());
    }

    public Boolean delete(List<Integer> ids) {
        return teacherSignRecordsMapper.delete(ids);
    }

    public TeacherSignRecords getById(Integer id) {
        return teacherSignRecordsMapper.getById(id);
    }
}
