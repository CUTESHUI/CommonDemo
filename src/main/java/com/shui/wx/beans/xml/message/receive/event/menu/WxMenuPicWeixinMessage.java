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
 * 弹出微信相册发图器的事件推送
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuPicWeixinMessage extends WxBaseReceiveEventMessage {

    private static final long serialVersionUID = -7503267041769037445L;

    @XStreamAlias("EventKey")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String eventKey;

    @XStreamAlias("SendPicsInfo")
    private WxMenuPicSysPhotoInfoMessage sendPicsInfo;

    @Override
    public String toString() {
        return super.toJsonString(this);
    }
}
