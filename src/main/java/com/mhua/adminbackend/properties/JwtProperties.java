package com.mhua.adminbackend.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mhua.jwt")
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String secretKey;

    /**
     * 过期时间
     */
    private long ttl;

    /**
     * token 名字
     */
    private String tokenName;

}
