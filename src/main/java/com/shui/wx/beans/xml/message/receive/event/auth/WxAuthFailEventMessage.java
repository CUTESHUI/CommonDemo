package com.shui.wx.beans.xml.message.receive.event.auth;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 资质认证失败 / 名称认证失败（这时虽然客户端不打勾，但仍有接口权限）
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxAuthFailEventMessage extends WxBaseReceiveEventMessage {

    private static final long serialVersionUID = 5838711228563157399L;
    //失败发生时间 (整形)，时间戳
    @XStreamAlias("FailTime")
    private Long failTime;

    @XStreamAlias("FailReason")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String failReason;
}
