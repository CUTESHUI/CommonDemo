package com.shui.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

public class MySensitiveWordHttpServletRequestWrapper extends HttpServletRequestWrapper {

    //定义一堆敏感词汇
    private List<String> sensitiveWords = Arrays.asList("卧槽", "滚蛋", "狗日的");


    public MySensitiveWordHttpServletRequestWrapper(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {

        String value = super.getParameter(name);

        if (value == null) {
            return null;
        }

        for (String sensitiveWord : sensitiveWords) {
            if (sensitiveWord.equals(value)) {
                value = "***";
            }
        }

        return value ;
    }
}
