package com.shui.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.util.*;

/**
 * sun公司知道开发者可能对request对象的方法不满意，于是提供了HttpServletRequestWrapper类去增强
 * 如果实现HttpServletRequest接口的话，要实现太多的方法了！
 */
public class MyHeaderHttpServletRequestWrapper extends HttpServletRequestWrapper {

    public MyHeaderHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    private Map<String, String> headers = new HashMap<>();

    public void addHeader(String name, String value) {
        headers.put(name, value);
    }

    @Override
    public String getHeader(String name) {
        String value = super.getHeader(name);

        if (headers.containsKey(name)) {
            value = headers.get(name);
        }

        return value;
    }

    @Override
    public Enumeration<String> getHeaderNames() {
        List<String> names = Collections.list(super.getHeaderNames());
        names.addAll(headers.keySet());

        return Collections.enumeration(names);
    }

    @Override
    public Enumeration<String> getHeaders(String name) {
        List<String> list = Collections.list(super.getHeaders(name));

        if (headers.containsKey(name)) {
            list.add(headers.get(name));
        }

        return Collections.enumeration(list);
    }
}
