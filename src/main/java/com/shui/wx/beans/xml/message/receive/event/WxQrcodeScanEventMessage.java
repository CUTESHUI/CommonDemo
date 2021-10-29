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
 * 扫描二维码,用户已关注时的事件推送
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxQrcodeScanEventMessage extends WxBaseReceiveEventMessage {

    private static final long serialVersionUID = -4843472699636864923L;
    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String eventKey;

    @XStreamAlias("Ticket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String ticket;
}
