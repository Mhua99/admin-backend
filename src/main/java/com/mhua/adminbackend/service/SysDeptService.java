package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.SysDeptQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysDept;
import com.mhua.adminbackend.pojo.vo.SysDeptTreeVO;
import com.mhua.adminbackend.result.PageResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysDeptService {

    PageResult<SysDept> list(SysDeptQueryDTO sysDeptQueryDTO);

    SysDept create(SysDept sysDept);

    SysDept update(SysDept sysDept);

    Boolean delete(List<Integer> ids);

    SysDept getById(Integer id);

    List<SysDeptTreeVO> getTree();
}
