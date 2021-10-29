package com.shui.wx.http.handler.host;

import com.shui.wx.config.LoadProp;
import com.shui.wx.http.base.HttpConnectionUtils;
import com.shui.wx.http.handler.base.BaseHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 获取微信服务器地址
 */
@Slf4j
public class WxHostHandler extends BaseHandler {

    /**
     * 获取微信服务器地址列表
     *
     * @param appid  appid
     * @param secret secret
     * @return 获取微信服务器地址列表，原始数据
     */
    public static String getHostList(String appid, String secret) throws Exception {
        String url = LoadProp.getValue(LoadProp.WEIXIN_HOST_GET);
        return HttpConnectionUtils.getWechatData(url, getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
    }
}
