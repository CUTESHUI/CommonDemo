package com.shui.wx.beans.xml.message.receive.event;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 自定义菜单，点击菜单跳转链接时的事件推送
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuLinkEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = 241947966931824186L;

    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String eventKey;
}
