package com.mhua.adminbackend.mapper;

import com.github.pagehelper.Page;
import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.pojo.dto.SysUserQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysUser;
import com.mhua.adminbackend.pojo.vo.SysUserVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

@Mapper
public interface SysUserMapper {
    Page<SysUser> list(SysUserQueryDTO sysUserQueryDTO);

    @AutoFill(OperationType.INSERT)
    void insert(SysUser sysUser);

    @AutoFill(OperationType.UPDATE)
    void update(SysUser sysUser);

    SysUser getById(Integer id);

    Boolean deleteByIds(@Param("ids") List<Integer> id);

    List<SysUser> getByNameAndPassword(@Param("username") String username, @Param("password") String password);

    int existsByUsername(@Param("username") String username);

    int existsByUsernameAndId(@Param("username") String username, @Param("id") Integer id);

    List<SysUserVO> getAll();
}
