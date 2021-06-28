package com.shui.filter;

import com.shui.exception.BaseException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MySensitiveWordFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将request和response强转成http协议的
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        // 解决全站的乱码问题
        httpServletRequest.setCharacterEncoding("UTF-8");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.setContentType("text/html;charset=UTF-8");

        MySensitiveWordHttpServletRequestWrapper mySensitiveWordHttpServletRequestWrapper = new MySensitiveWordHttpServletRequestWrapper(httpServletRequest);

        // 使用增强的request
        filterChain.doFilter(mySensitiveWordHttpServletRequestWrapper, httpServletResponse);
    }

}
