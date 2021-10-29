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
 * 弹出系统拍照发图的事件推送
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuPicSysPhotoMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = -2435908162906954285L;

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
