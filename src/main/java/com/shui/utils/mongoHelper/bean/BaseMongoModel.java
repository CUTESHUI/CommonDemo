package com.shui.utils.mongoHelper.bean;

import org.springframework.data.annotation.Id;

import java.io.Serializable;

public class BaseMongoModel implements Serializable {
	@Id
    String id;

	@CreateTime
    Long createTime;

	@UpdateTime
    Long updateTime;
	
}
