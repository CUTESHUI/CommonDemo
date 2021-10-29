package com.shui.wx.beans.xml.message.send.base;

import com.shui.wx.beans.xml.message.base.WxBaseMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信公众号发送XML消息基础类
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxBaseSendMessage extends WxBaseMessage {
    private static final long serialVersionUID = 8125906444213311550L;

    @XStreamAlias("CreateTime")
    private Long createTime;
}
