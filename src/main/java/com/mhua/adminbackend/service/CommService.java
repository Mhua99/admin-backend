package com.mhua.adminbackend.service;

import com.mhua.adminbackend.result.PageResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface CommService {
    PageResult<Map<String, Object>> list(String url, Map<String, Object> params);

    Object getTableField(String url);

    Map<String, Object> insert(String sign, Map<String, Object> params);

    Map<String, Object> update(String sign, Map<String, Object> params);

    Boolean delete(String sign, Integer id);
}
