package com.shui.wx.beans.xml.message.receive.event.base;

import com.shui.wx.beans.xml.message.receive.base.WxBaseReceiveMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信接收事件基础类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxBaseReceiveEventMessage extends WxBaseReceiveMessage {
    private static final long serialVersionUID = -4727976028597439732L;

    @XStreamAlias("Event")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String event;

}
