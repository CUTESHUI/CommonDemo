package com.shui.wx.beans.xml.message.receive.event.menu;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 点击菜单跳转链接时的事件推送
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuViewEventMessage extends WxBaseReceiveEventMessage {

    private static final long serialVersionUID = -7406152795873059169L;

    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String eventKey;

    @XStreamAlias("MenuId")
    private String menuId;
}
