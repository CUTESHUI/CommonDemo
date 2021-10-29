package com.shui.wx.beans.xml.message.send.news;

import com.shui.wx.beans.xml.message.send.base.WxBaseSendMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 回复图文消息
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxSendNewsMessage extends WxBaseSendMessage {
    private static final long serialVersionUID = -958376690542758418L;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType = "news";

    @XStreamAlias("ArticleCount")
    private Integer articleCount;

    @XStreamAlias("Articles")
    private List<WxSendNewsItemMessage> articles;
}
