package com.shui.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyHeaderFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将request和response强转成http协议的
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        MyHeaderHttpServletRequestWrapper myHeaderRequest = new MyHeaderHttpServletRequestWrapper(httpServletRequest);
        myHeaderRequest.addHeader("name", "tom");

        // 使用增强的request
        // 执行这一句，说明放行（让下一个过滤器执行，如果没有过滤器了，就执行执行目标资源/请求）
        filterChain.doFilter(myHeaderRequest, httpServletResponse);
    }

}
