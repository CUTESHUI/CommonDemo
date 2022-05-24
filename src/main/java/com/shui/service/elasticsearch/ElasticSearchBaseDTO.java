package com.shui.service.elasticsearch;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.io.Serializable;

@Data
public class ElasticSearchBaseDTO implements Serializable {

    private String id;

    private JSONObject filedJson;
}
