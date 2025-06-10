package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.SysMenuQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface SysMenuMapper {


    Page<SysMenu> list(SysMenuQueryDTO sysMenuQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(SysMenu sysMenu);

    @AutoFill(OperationType.UPDATE)
    void update(SysMenu sysMenu);

    @Select("select * from sys_menu where id = #{id}")
    SysMenu getById(Integer id);

    Boolean delete(List<Integer> ids);
}
