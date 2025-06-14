package com.mhua.adminbackend.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mhua.adminbackend.exception.BaseException;
import com.mhua.adminbackend.pojo.dto.SysUserLoginDTO;
import com.mhua.adminbackend.pojo.dto.SysUserQueryDTO;
import com.mhua.adminbackend.pojo.entity.SysUser;
import com.mhua.adminbackend.mapper.SysUserMapper;
import com.mhua.adminbackend.pojo.vo.SysUserLoginVO;
import com.mhua.adminbackend.pojo.vo.SysUserVO;
import com.mhua.adminbackend.properties.JwtProperties;
import com.mhua.adminbackend.result.PageResult;
import com.mhua.adminbackend.service.SysUserService;
import com.mhua.adminbackend.constant.TipConstant;
import com.mhua.adminbackend.utils.JwtUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Autowired
    private JwtProperties jwtProperties;

    public PageResult<SysUser> list(SysUserQueryDTO sysUserQueryDTO) {
        int page = sysUserQueryDTO.getPage() == null ? 1 : sysUserQueryDTO.getPage();
        int pageSize = sysUserQueryDTO.getPageSize() == null ? 10 : sysUserQueryDTO.getPageSize();
        PageHelper.startPage(page, pageSize);

        Page<SysUser> pageList = sysUserMapper.list(sysUserQueryDTO);
        return new PageResult<>(pageList.getTotal(), pageList.getResult());
    }

    @Transactional
    public SysUserVO insert(SysUser sysUser) {

        String password = sysUser.getPassword();
        String username = sysUser.getUsername();

        if(password == null || password.isEmpty()) {
            throw new BaseException("密码不能为空");
        }

        if (sysUserMapper.existsByUsername(username) > 0) {
            throw new BaseException("该账号已存在");
        }

        /**
         * 密码加密
         */
        password = DigestUtils.md5DigestAsHex(password.getBytes());

        /**
         * 插入用户数据
         */
        sysUser.setPassword(password);
        sysUserMapper.insert(sysUser);

        /**
         * 返回插入的数据
         */
        SysUser userData = sysUserMapper.getById(sysUser.getId());
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(userData, sysUserVO);

        return sysUserVO;
    }

    public SysUserVO update(SysUser sysUser) {
        Integer userId = sysUser.getId();

        if (userId == null || userId <= 0) {
            throw new BaseException("id不能为空");
        }

        if (sysUserMapper.existsByUsernameAndId(sysUser.getUsername(), userId) > 0) {
            throw new BaseException("该账号已存在");
        }

        /**
         * 更新用户数据
         */
        sysUserMapper.update(sysUser);

        /**
         * 返回更新的数据
         */
        SysUser userData = sysUserMapper.getById(userId);
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(userData, sysUserVO);

        return sysUserVO;
    }

    public Boolean delete(List<Integer> ids) {
        return sysUserMapper.deleteByIds(ids);
    }

    public SysUserVO getById(Integer id) {
        SysUser userData = sysUserMapper.getById(id);
        SysUserVO sysUserVO = new SysUserVO();
        BeanUtils.copyProperties(userData, sysUserVO);

        return sysUserVO;
    }

    public SysUserLoginVO login(SysUserLoginDTO sysUserLoginDTO) {
        String username = sysUserLoginDTO.getUsername();
        String password = sysUserLoginDTO.getPassword();

        if(username == null || username.isEmpty() || password == null || password.isEmpty()) {
            throw new BaseException("用户名或密码不能为空");
        }

        List<SysUser> userList = sysUserMapper.getByNameAndPassword(sysUserLoginDTO.getUsername(), sysUserLoginDTO.getPassword());
        int userCount = userList.size();

        if(userCount == 0) {
            throw new BaseException("用户名或密码错误");
        }

        if(userCount > 1) {
            throw new BaseException(TipConstant.SYSTEM_EXCEPTION);
        }

        SysUser user = userList.get(0);
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getId());

        String token = JwtUtil.createJWT(jwtProperties.getSecretKey(), jwtProperties.getTtl(), claims);

        return new SysUserLoginVO(token, user.getId());
    }
}