package com.ncamc.admin.servlet;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 过滤器是出于客户端和服务器端目标资源之间的过滤技术，是一个中间层。访问服务器资源时，访问某个特定资源时可以将请求拦截下来，完成一些特殊功能。
 * 在Servlet之前，客户端发送请求，先经过filter，再到达Servlet。
 */
@Slf4j
//@WebFilter(urlPatterns = {"/css/*","/images/*"})
public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MyFilter初始化完成。。");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        log.info("MyFilter工作中，过滤url：{}",req.getRequestURL());
        chain.doFilter(request,response);
    }

    @Override
    public void destroy() {
        log.info("MyFilter已销毁。。");
    }
}
