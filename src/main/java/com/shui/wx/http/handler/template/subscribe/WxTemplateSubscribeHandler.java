package com.shui.wx.http.handler.template.subscribe;

import com.shui.wx.beans.json.template.subscribe.WxTemplateSubscribe;
import com.shui.wx.config.LoadProp;
import com.shui.wx.http.base.HttpConnectionUtils;
import com.shui.wx.http.handler.base.BaseHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * 一次性订阅消息,通过API推送订阅模板消息给到授权微信用户
 */
@Slf4j
public class WxTemplateSubscribeHandler extends BaseHandler {

    /**
     * 一次性订阅消息,通过API推送订阅模板消息给到授权微信用户
     * @param appid appid
     * @param secret secret
     * @param wxTemplateSubscribe 格式如下：
     *  {
     *     "data": {
     *         "content": {
     *             "color": "#777777",
     *             "value": "你好"
     *         }
     *     },
     *     "miniprogram": {
     *         "appid": "test",
     *         "pagepath": "test"
     *     },
     *     "scene": "sence",
     *     "template_id": "template_id",
     *     "title": "title",
     *     "touser": "toUser",
     *     "url": "http://www.baidu.com"
     * }
     * @return
     * {
     * “errcode”:0,
     * “errmsg”:”ok”
     * }
     * @throws Exception
     */
    public static String subscribeTemplate(String appid, String secret, WxTemplateSubscribe wxTemplateSubscribe) throws Exception{
        return HttpConnectionUtils.postWechatData(LoadProp.getValue(LoadProp.WEIXIN_TEMPLATE_SUBSCRIBE), wxTemplateSubscribe.toJsonString(), getAccessTokenNameValuePairs(appid, secret), null, HttpConnectionUtils.TYPE_STREAM);
    }
}
