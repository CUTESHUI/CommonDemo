package com.shui;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.shui.dao")
@SpringBootApplication
public class CommonDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CommonDemoApplication.class, args);
    }

//    @PostConstruct
//    public void init() {
//        // 解决netty启动冲突问题
//        // see Netty4Utils.setAvailableProcessors()
//        System.setProperty("es.set.netty.runtime.available.processors", "false");
//    }
}
