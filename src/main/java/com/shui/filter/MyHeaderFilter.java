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

        // 原理：多个子类，实例谁就用谁，没重写就用父类的，达到重用代码的作用
        // 过滤链，传入前一个子类
        //        从第一个开始，一旦重写且之后再没被重写，就一直作用
        //                    一旦重写且之后再被重写，就以被重写作用
        //                    没有被重写的，不管是不是拓展抽象类的，都以第一个有该方法体的父类作用
        // 最后以过滤链的最后一个为准，找父类被重写的
        // 适配器模式
        // 以这里为例：现在是重写头部的子类b，调用头部的自然是b自己的，传入了前一个子类a，b调用x，b没有重写就找父类c，c没有就找d，d有但被a，p重写了，同时传入的是a，于是调用a的x
        MyHeaderHttpServletRequestWrapper myHeaderRequest = new MyHeaderHttpServletRequestWrapper(httpServletRequest);
        myHeaderRequest.addHeader("name", "tom");

        // 使用增强的request
        // 执行这一句，说明放行（让下一个过滤器执行，如果没有过滤器了，就执行执行目标资源/请求）
        filterChain.doFilter(myHeaderRequest, httpServletResponse);
    }

}
