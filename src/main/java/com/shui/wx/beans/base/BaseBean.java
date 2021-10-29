package com.shui.wx.beans.base;

import com.alibaba.fastjson.JSONObject;
import com.shui.wx.utils.map.ReflectMap;
import com.shui.wx.utils.xml.handler.XStreamHandler;

import java.io.Serializable;
import java.util.Map;

/**
 * 最基础的bean
 */
public abstract class BaseBean implements Serializable {

    @Override
    public String toString() {
        return super.toString();
    }

    public String toJsonString(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public String toXmlString(Object obj) {
        return XStreamHandler.toXml(obj);
    }

    public String toJsonString() {
        return toJsonString(this);
    }

    public String toXmlString() {
        return toXmlString(this);
    }

    public Map<String, Object> toMap(Object obj) {
        return ReflectMap.beanToMap(obj);
    }

    public Map<String, Object> toMap() {
        return ReflectMap.beanToMap(this);
    }
}
