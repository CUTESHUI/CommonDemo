package com.shui.wx.beans.xml.message.receive.base;

import com.shui.wx.beans.xml.message.base.WxBaseMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公众号接收消息的基础类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxBaseReceiveMessage extends WxBaseMessage {
    private static final long serialVersionUID = 7096703420709872400L;
    @XStreamAlias("CreateTime")
    private Long createTime;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType;


    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
