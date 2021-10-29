package com.shui.wx.beans.json.base;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.shui.wx.beans.base.BaseBean;

/**
 * 基础的JsonBean
 */
public class BaseJsonBean extends BaseBean {

    private static final long serialVersionUID = -1446274398967377397L;

    public String toString(Object obj) {
        return JSONObject.toJSONString(obj);
    }

    public String toJsonArrayString(Object obj) {
        return JSONArray.toJSONString(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
