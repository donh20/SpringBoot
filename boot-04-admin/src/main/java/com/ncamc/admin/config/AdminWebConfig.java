package com.ncamc.admin.config;

import com.ncamc.admin.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AdminWebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor())
                .addPathPatterns("/**") //所有的请求都会被拦截,包括静态资源
                .excludePathPatterns("/login","/index","/","/error" //放行的请求
                        ,"/css/**","/fonts/**","/images/**","/js/**");
    }
}
