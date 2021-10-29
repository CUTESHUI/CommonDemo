package com.shui.wx.beans.xml.message.receive.text;

import com.shui.wx.beans.xml.message.receive.base.WxBaseReceiveMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信文本消息
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxReceiveTextMessage extends WxBaseReceiveMessage {
    private static final long serialVersionUID = -7346355738576226082L;

    @XStreamAlias("Content")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String content;

    @XStreamAlias("MsgId")
    private Long msgId;

    @Override
    public String toString(){
        return toJsonString(this);
    }
}
