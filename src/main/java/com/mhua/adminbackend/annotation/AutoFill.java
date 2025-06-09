package com.mhua.adminbackend.annotation;
import com.mhua.adminbackend.enumeration.OperationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Classname AutoFill
 * @Description 自定义注解，用于自动填充公共字段
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AutoFill {
    /*指定操作类型：update || insert*/
    OperationType value();
}
