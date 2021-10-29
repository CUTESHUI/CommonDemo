package com.shui.wx.beans.json.factory.json;

import com.shui.wx.beans.json.qrcode.WxQrcodeForever;
import com.shui.wx.beans.json.qrcode.WxQrcodeSence;
import com.shui.wx.beans.json.qrcode.WxQrcodeSenceId;
import com.shui.wx.beans.json.qrcode.WxQrcodeTemporary;

/**
 * 请求微信生成二维码的参数
 */
public class QrcodeParamsFactory {

    /**
     * 生成永久二维码，传递Int类型参数
     */
    public static WxQrcodeForever getWxQrcodeForeverByIntParams(Integer qrcodeParams){
        WxQrcodeSenceId wxQrcodeSenceId = new WxQrcodeSenceId();
        wxQrcodeSenceId.setScene_id(qrcodeParams);
        WxQrcodeSence wxQrcodeSence = new WxQrcodeSence(wxQrcodeSenceId);
        WxQrcodeForever wxQrcodeForever = new WxQrcodeForever();
        wxQrcodeForever.setAction_info(wxQrcodeSence);
        wxQrcodeForever.setAction_name("QR_LIMIT_SCENE");
        return wxQrcodeForever;
    }

    /**
     * 生成永久二维码，传递Spring类型的参数
     */
    public static WxQrcodeForever getWxQrcodeForeverByStringParams(String qrcodeParams){
        WxQrcodeSenceId wxQrcodeSenceId = new WxQrcodeSenceId();
        wxQrcodeSenceId.setScene_str(qrcodeParams);
        WxQrcodeSence wxQrcodeSence = new WxQrcodeSence(wxQrcodeSenceId);
        WxQrcodeForever wxQrcodeForever = new WxQrcodeForever();
        wxQrcodeForever.setAction_info(wxQrcodeSence);
        wxQrcodeForever.setAction_name("QR_LIMIT_STR_SCENE");
        return wxQrcodeForever;
    }

    /**
     * 生成临时二维码，传递Int类型的参数和有效时间
     */
    public static WxQrcodeTemporary getWxQrcodeTemporaryByIntParams(Integer qrcoceParams, Integer expireTime){
        WxQrcodeSenceId wxQrcodeSenceId = new WxQrcodeSenceId();
        wxQrcodeSenceId.setScene_id(qrcoceParams);
        WxQrcodeSence wxQrcodeSence = new WxQrcodeSence(wxQrcodeSenceId);
        WxQrcodeTemporary wxQrcodeTemporary = new WxQrcodeTemporary();
        wxQrcodeTemporary.setAction_info(wxQrcodeSence);
        wxQrcodeTemporary.setAction_name("QR_SCENE");
        wxQrcodeTemporary.setExpire_seconds(expireTime);
        return wxQrcodeTemporary;
    }

    /**
     * 生成临时二维码，传递String类型的参数和有效时间
     */
    public static WxQrcodeTemporary getWxQrcodeTemporaryByStringParams(String qrcoceParams, Integer expireTime){
        WxQrcodeSenceId wxQrcodeSenceId = new WxQrcodeSenceId();
        wxQrcodeSenceId.setScene_str(qrcoceParams);
        WxQrcodeSence wxQrcodeSence = new WxQrcodeSence(wxQrcodeSenceId);
        WxQrcodeTemporary wxQrcodeTemporary = new WxQrcodeTemporary();
        wxQrcodeTemporary.setAction_info(wxQrcodeSence);
        wxQrcodeTemporary.setAction_name("QR_STR_SCENE");
        wxQrcodeTemporary.setExpire_seconds(expireTime);
        return wxQrcodeTemporary;
    }
}
