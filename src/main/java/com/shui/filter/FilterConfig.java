package com.shui.filter;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.DispatcherType;

@Configuration
public class FilterConfig {

    @Bean
    public FilterRegistrationBean myExceptionFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new MyExceptionFilter());
        registration.addUrlPatterns("/*");
        registration.setName("myExceptionFilter");
        registration.setOrder(4);
        return registration;
    }

    @Bean
    public FilterRegistrationBean myHeaderFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new MyHeaderFilter());
        registration.addUrlPatterns("/*");
        registration.setName("myHeaderFilter");
        registration.setOrder(3);
        return registration;
    }

    @Bean
    public FilterRegistrationBean mySensitiveWordFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setDispatcherTypes(DispatcherType.REQUEST);
        registration.setFilter(new MySensitiveWordFilter());
        registration.addUrlPatterns("/*");
        registration.setName("mySensitiveWordFilter");
        registration.setOrder(2);
        return registration;
    }

}
