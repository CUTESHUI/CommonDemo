package com.shui.wx.beans.xml.message.receive.event;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信关注/取消关注事件推送的XML
 */
@XStreamAlias("xml")
@Data
@EqualsAndHashCode(callSuper = false)
public class WxSubscribeEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = 2108639063874991944L;

}
