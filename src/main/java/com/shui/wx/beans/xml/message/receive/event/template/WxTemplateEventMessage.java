package com.shui.wx.beans.xml.message.receive.event.template;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 发送模板消息后微信推送的XML数据
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxTemplateEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = 337278476453581332L;

    @XStreamAlias("MsgID")
    private Long msgId;

    @XStreamAlias("Status")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String status;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
