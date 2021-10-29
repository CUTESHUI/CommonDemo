package com.shui.config.mybatis;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.context.annotation.Configuration;

import java.util.Date;


@Configuration
public class MyMetaObjectHandler implements MetaObjectHandler {

    private final static String CREATE_DATE = "createDate";
    private final static String CREATOR = "creator";
    private final static String UPDATE_DATE = "updateDate";
    private final static String UPDATOR = "updator";

    @Override
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName(CREATE_DATE, new Date(), metaObject);
        this.setFieldValByName(CREATOR, 701004L, metaObject);
}

    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(UPDATE_DATE, new Date(), metaObject);
        this.setFieldValByName(UPDATOR, 701004L, metaObject);
    }
}
