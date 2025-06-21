package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.mapper.CommMapper;
import com.mhua.adminbackend.mapper.ViewMapper;
import com.mhua.adminbackend.pojo.entity.View;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.CommService;
import com.mhua.adminbackend.utils.DateFormatterUtils;
import com.mhua.adminbackend.utils.DynamicFieldDateFormatter;
import com.mhua.adminbackend.utils.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CommServiceImpl implements CommService {

    @Autowired
    private CommMapper commMapper;

    @Autowired
    private ViewMapper viewMapper;


    public PageResult<Map<String, Object>> list(String sign, Map<String, Object> params) {

        String tableName = commMapper.getTableNameByySign(sign);

        if (tableName == null || tableName.isEmpty()) {
            return new PageResult<>(0, new ArrayList<>());
        }

        // 提取分页数据
        Integer page = NumberUtils.checkInteger(Integer.parseInt((String) params.get("page")), 1);
        Integer pageSize = NumberUtils.checkInteger(Integer.parseInt((String) params.get("pageSize")), 10);

        // 移除分页数据
        params.remove("page");
        params.remove("pageSize");

        PageHelper.startPage(page, pageSize);

        List<Map<String, Object>> data = commMapper.queryData(tableName, params);

        // 自动识别并格式化所有时间字段
        DynamicFieldDateFormatter.formatDateTimeFields(data);

        // 获取分页后的总数（由 PageHelper 自动从数据库查询 count）
        long total = ((com.github.pagehelper.Page<Map<String, Object>>) data).getTotal();

        return new PageResult<>((int) total, data);
    }

    public View getTableField(String sign) {
        return viewMapper.getBySign(sign);
    }

    public Map<String, Object> insert(String sign, Map<String, Object> params) {
        String tableName = commMapper.getTableNameByySign(sign);
        commMapper.insert(tableName, params);

        Map<String, Object> getById = commMapper.getById(tableName, ((Number) params.get("id")).intValue());
        getById.compute("create_time", (k, createTime) -> DateFormatterUtils.formatIfTime(createTime));
        getById.compute("update_time", (k, updateTime) -> DateFormatterUtils.formatIfTime(updateTime));

        return getById;
    }

    public Map<String, Object> update(String sign, Map<String, Object> params) {
        String tableName = commMapper.getTableNameByySign(sign);
        commMapper.update(tableName, params, (Serializable) params.get("id"));
        Map<String, Object> getById = commMapper.getById(tableName, ((Number) params.get("id")).intValue());

        Object createTime = getById.get("create_time");
        Object updateTime = getById.get("update_time");

        getById.put("create_time", DateFormatterUtils.formatIfTime(createTime));
        getById.put("update_time", DateFormatterUtils.formatIfTime(updateTime));

        return getById;
    }

    public Boolean delete(String sign, Integer id) {
        String tableName = commMapper.getTableNameByySign(sign);
        return commMapper.delete(tableName, id);
    }
}
