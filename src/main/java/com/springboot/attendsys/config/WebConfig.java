package com.springboot.attendsys.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

/**
 * Created by jiangyunxiong on 2018/5/22.
 *
 * 自定参数解析器, 作用：改变SpringMVC的Controller传入参数，实现可以User替换Token做为参数从登陆页面传到商品列表页面
 */
@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {
    @Autowired
    UserArgumentResolver userArgumentResolver;
    /**
     * SpringMVC框架回调addArgumentResolvers，然后给Controller的参数赋值
     * @param argumentResolvers
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(userArgumentResolver);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //Windows下
        registry.addResourceHandler("/cover/**").addResourceLocations("file:D:/attStatic/cover/");
        registry.addResourceHandler("/photo/**").addResourceLocations("file:D:/attStatic/photo/");
        super.addResourceHandlers(registry);
        /*因为配置了拦截器，所以使用spring-resources-static-locations:配置
        本地静态资源路径无效，重写resourceshandler方法才成功配置 */
    }
}
