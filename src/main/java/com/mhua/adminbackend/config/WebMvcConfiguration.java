package com.mhua.adminbackend.config;

import com.mhua.adminbackend.interceptor.JwtTokenAdminInterceptor;
import com.mhua.adminbackend.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.util.List;

/**
 * 配置类，注册web层相关组件
 */
@Configuration
@Slf4j
public class WebMvcConfiguration extends WebMvcConfigurationSupport {

    @Autowired
    private JwtTokenAdminInterceptor jwtTokenAdminInterceptor;

    /**
     * 注册自定义拦截器
     *
     * @param registry
     */
    protected void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        List<String> excludePaths = List.of(
                "/sys/user/login",
                "/v3/api-docs/swagger-config",
                "/v3/api-docs/default"
        );

        registry.addInterceptor(jwtTokenAdminInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns(excludePaths);
    }

    /**
     * 设置静态资源映射
     *
     * @param registry
     */
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("准备设置静态资源映射");
        registry.addResourceHandler("/doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    /**
     * 扩展springmvc的消息转换器
     *
     * @param converters
     */
    @Override
    protected void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        log.info("扩展消息转换器...，用于时间格式化");
        //创建一个消息转换器对象
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//
//        //为消息转换器设置一个对象转换器，将java对象序列化为json数据
//        converter.setObjectMapper(new JacksonObjectMapper());
//
//        //将自定义的消息转换器加入容器，这样springmvc框架就可以使用了
//        //index=0 这样可以让自定义消息转换器优先生效
//        converters.add(0, converter);
        // 先让Knife4j的转换器处理OpenAPI文档请求
//        converters.stream()
//                .filter(c -> c.getClass() == MappingJackson2HttpMessageConverter.class)
//                .findFirst()
//                .ifPresent(c -> {
//                    // 只在非Knife4j的转换器上应用自定义配置
//                    if (!c.toString().contains("OpenApiResource")) {
//                        MappingJackson2HttpMessageConverter customConverter =
//                                new MappingJackson2HttpMessageConverter(new JacksonObjectMapper());
//                        converters.add(converters.indexOf(c) + 1, customConverter); // 插入到默认转换器之后
//                    }
//                });

    }
}
