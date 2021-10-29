package com.shui.wx.http.handler.quota;

import com.shui.wx.beans.json.quota.WxQuota;
import com.shui.wx.config.LoadProp;
import com.shui.wx.http.base.HttpConnectionUtils;
import com.shui.wx.http.handler.base.BaseHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 微信API调用次数清0
 */
@Slf4j
public class WxQuotaHandler extends BaseHandler {

    /**
     * 调用 API 次数清零
     * @param appid appid
     * @param secret secret
     * @return
     * 成功：{ "errcode" :0, "errmsg" : "ok" }
     * 失败：{ "errcode" :48006, "errmsg" : "forbid to clear quota because of reaching the limit" }
     * @throws Exception
     */
    public static String quotaClear(String appid, String secret) throws Exception{
        return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEXIN_QUOTA_CLEAR), new WxQuota(appid).toString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM );
    }
}
