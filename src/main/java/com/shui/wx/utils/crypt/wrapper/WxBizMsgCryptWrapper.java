package com.shui.wx.utils.crypt.wrapper;


import com.shui.wx.utils.crypt.WXBizMsgCrypt;
import com.shui.wx.utils.crypt.bean.WxCryptXml;
import com.shui.wx.utils.xml.handler.XStreamHandler;

/**
 * 消息体加解密包装类
 */
public class WxBizMsgCryptWrapper {

    /**
     * 生成WXBizMsgCrypt消息体加解密句柄
     *
     * @param encodingAesKey 微信公众号上配置的encodingAesKey
     * @param token          微信公众号上配置的token，并非请求接口获取的access_token
     * @param appId          appId
     * @return XBizMsgCrypt消息体加解密句柄
     */
    private static WXBizMsgCrypt getWXBizMsgCrypt(String encodingAesKey, String token, String appId) throws Exception {
        return new WXBizMsgCrypt(token, encodingAesKey, appId);
    }

    /**
     * 对指定的消息进行加密
     *
     * @param encodingAesKey 微信公众号上配置的encodingAesKey
     * @param token          微信公众号上配置的token，并非请求接口获取的access_token
     * @param timestamp      时间，精确到秒
     * @param nonce          随机字符串
     * @param appId          appId
     * @param msg            : 待加密的字符串
     * @return 加密后的结果字符串
     * @throws Exception
     */
    public static String encryptMsg(String encodingAesKey, String token, String timestamp, String nonce, String appId, String msg) throws Exception {
        return getWXBizMsgCrypt(encodingAesKey, token, appId).encryptMsg(msg, timestamp, nonce);
    }

    /**
     * 对指定的消息进行解密
     *
     * @param encodingAesKey 微信公众号上配置的encodingAesKey
     * @param token          微信公众号上配置的token，并非请求接口获取的access_token
     * @param appId          appId
     * @param msg            : 待解密的字符串
     * @return 解密后的结果字符串
     * @throws Exception
     */
    public static String decryptMsg(String encodingAesKey, String token, String appId, String msg) throws Exception {
        String format = "<message><ToUserName><![CDATA[toUser]]></ToUserName><Encrypt><![CDATA[%1$s]]></Encrypt></message>";
        WxCryptXml wxCryptXml = XStreamHandler.toBean(msg, WxCryptXml.class);
        String fromXML = String.format(format, wxCryptXml.getEncrypt());
        return getWXBizMsgCrypt(encodingAesKey, token, appId).decryptMsg(wxCryptXml.getMsgSignature(), wxCryptXml.getTimeStamp(), wxCryptXml.getNonce(), fromXML);
    }
}
