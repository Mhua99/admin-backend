package com.mhua.adminbackend.service;

import com.mhua.adminbackend.pojo.dto.TableDTO;
import com.mhua.adminbackend.pojo.entity.Table;
import com.mhua.adminbackend.pojo.vo.TableVO;
import com.mhua.adminbackend.result.PageResult;

import java.util.List;

public interface DatabaseService {
    void insert(TableDTO tableDTO);

    PageResult<TableVO> list(TableDTO tableDTO);

    void update(TableDTO tableDTO);

    Boolean delete(Integer id);

    TableVO findTableAndFields(Integer id);

    List<Table> getAllTable();
}
