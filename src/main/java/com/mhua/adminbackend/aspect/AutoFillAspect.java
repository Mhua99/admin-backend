package com.mhua.adminbackend.aspect;

import com.mhua.adminbackend.annotation.AutoFill;
import com.mhua.adminbackend.constant.AutoFillConstant;
import com.mhua.adminbackend.enumeration.OperationType;
import com.mhua.adminbackend.utils.BaseContext;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class AutoFillAspect {

    @Pointcut("execution(* com.mhua.adminbackend.mapper.*.*(..)) && @annotation(com.mhua.adminbackend.annotation.AutoFill)")
    public void autoFillPointCut() {
    }

    /**
     * 前置通知 为公共字段赋值
     */
    @Before("autoFillPointCut()")
    public void autoFill(JoinPoint joinPoint) {
        log.info("开始进行公共字段的自动填充...");
        /*获取拦截到的方法的数据库操作类型*/
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        AutoFill autoFill = signature.getMethod().getAnnotation(AutoFill.class);
        OperationType type = autoFill.value();  //拿到了操作类型
        /*获取方法的参数（实体对象）*/
        Object[] args = joinPoint.getArgs();
        /* 防止空指针异常 */
        if (args == null || args.length == 0) {
            return;
        }
        Object arg = args[0];
        LocalDateTime time = LocalDateTime.now();
        Integer id = BaseContext.getCurrentId();

        if(args.length >1  && args[1] != null){
            Object argMap = args[1];

            if (argMap instanceof Map<?, ?> rawMap) {
                Map<String, Object> map = (Map<String, Object>) rawMap;
                if(type == OperationType.INSERT) {
                    map.put("create_time", time);
                    map.put("update_time", time);
                    map.put("create_user", id);
                    map.put("update_user", id);
                } else {
                    map.put("update_time", time);
                    map.put("update_user", id);
                }
            }
        }
        else if (arg instanceof List<?> entities) {
            for (Object entity : entities) {
                fillFields(entity, type, time, id);
            }
        } else {
            fillFields(arg, type, time, id);
        }
    }

    private void fillFields(Object entity, OperationType type, LocalDateTime time, Integer id) {
        try {
            if (type == OperationType.INSERT) {
                Method setCreateTime = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_TIME, LocalDateTime.class);
                Method setCreateUser = entity.getClass().getMethod(AutoFillConstant.SET_CREATE_USER, Integer.class);
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Integer.class);

                setCreateTime.invoke(entity, time);
                setCreateUser.invoke(entity, id);
                setUpdateTime.invoke(entity, time);
                setUpdateUser.invoke(entity, id);
            } else if (type == OperationType.UPDATE) {
                Method setUpdateTime = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_TIME, LocalDateTime.class);
                Method setUpdateUser = entity.getClass().getMethod(AutoFillConstant.SET_UPDATE_USER, Integer.class);

                setUpdateTime.invoke(entity, time);
                setUpdateUser.invoke(entity, id);
            }
        } catch (Exception e) {
            log.error("自动填充字段失败", e);
        }
    }
}
