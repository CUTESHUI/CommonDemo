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
 * 扫码推事件且弹出“消息接收中”提示框的事件推送
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuScanCodeWaitMsgEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = -5696874453807651210L;

    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String eventKey;

    @XStreamAlias("ScanCodeInfo")
    private WxMenuScanCodeItemEventMessage scanCodeInfo;
}
