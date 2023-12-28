package com.ncamc.admin.config;

import com.ncamc.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 1. MyServlet
 * 2. DispatcherServlet
 *
 * DispatcherServlet是如何配置进来的?
 * DispatcherServletAutoConfiguration里直接往容器里放了一个dispatcherServlet,还放了一个DispatcherServletRegistrationBean
 * 观察发现,DispatcherServletRegistrationBean就是一个ServletRegistrationBean
 * public class DispatcherServletRegistrationBean extends ServletRegistrationBean<DispatcherServlet>
 * 通过ServletRegistrationBean<DispatcherServlet>把dispatcherServlet配置进来
 */
@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") //所有的请求都会被拦截,包括静态资源
                .excludePathPatterns("/login", "/index", "/", "/templates/error",
                        //放行的请求
                        "/css/**",
                        "/fonts/**",
                        "/images/**",
                        "/js/**",
                        "/city",
                        "/sql");
    }
}
