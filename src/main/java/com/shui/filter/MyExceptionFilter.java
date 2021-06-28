package com.shui.filter;

import javax.servlet.*;
import java.io.IOException;

public class MyExceptionFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
//            int i = 1 / 0;
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            servletRequest.setAttribute("filterException", e);
            // 将异常分发到/filter/exception对应的控制器
            servletRequest.getRequestDispatcher("/filter/exception").forward(servletRequest, servletResponse);
        }
    }

}
