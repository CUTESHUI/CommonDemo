package com.shui.wx.http.handler.shorturl;

import com.shui.wx.beans.json.shorturl.WxShortUrl;
import com.shui.wx.beans.json.shorturl.WxShortUrlResp;
import com.shui.wx.config.LoadProp;
import com.shui.wx.http.base.HttpConnectionUtils;
import com.shui.wx.http.handler.base.BaseHandler;
import com.shui.wx.utils.json.JsonUtils;

/**
 * 微信短连接处理器
 */
public class WxWxShortUrlHandler extends BaseHandler {

    /**
     * 生成短连接
     * @param appid appid
     * @param secret secret
     * @param wxShortUrl 生成短连接参数
     * {\"action\":\"long2short\",\"long_url\":\"http://wap.koudaitong.com/v2/showcase/goods?alias=128wi9shh&spm=h56083&redirect_count=1\"}
     * @return
     * {"errcode":0,"errmsg":"ok","short_url":"http:\/\/w.url.cn\/s\/AvCo6Ih"}
     * 参数	            说明
     * errcode	        错误码。
     * errmsg	        错误信息。
     * short_url	    短链接。
     * @throws Exception
     */
    public static WxShortUrlResp createWxShortUrl(String appid, String secret, WxShortUrl wxShortUrl) throws Exception{
        return JsonUtils.json2Bean(HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_SHORTURL), wxShortUrl.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM), WxShortUrlResp.class);
    }
}
