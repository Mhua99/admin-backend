package com.mhua.adminbackend.mapper;

import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import org.apache.ibatis.annotations.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Mapper
public interface CommMapper {

    String getTableNameByySign(String sign);

    @SelectProvider(type = SqlProvider.class, method = "searchTableSql")
    List<Map<String, Object>> queryData(@Param("tableName") String tableName,
                                        @Param("conditions") Map<String, Object> conditions);

    @InsertProvider(type = SqlProvider.class, method = "insertDataSql")
    @Options(useGeneratedKeys = true, keyProperty = "data.id", keyColumn = "id")
    @AutoFill(OperationType.INSERT)
    void insert(@Param("tableName") String tableName, @Param("data") Map<String, Object> data);

    @UpdateProvider(type = SqlProvider.class, method = "updateDataSql")
    @AutoFill(OperationType.UPDATE)
    int update(@Param("tableName") String tableName, @Param("data") Map<String, Object> data, @Param("id") Serializable id);

    @Select("SELECT * FROM ${tableName} WHERE id = #{id}")
    Map<String, Object> getById(@Param("tableName") String tableName, @Param("id") Integer id);

    Boolean delete(String tableName, Integer id);
}
