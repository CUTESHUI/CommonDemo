package com.shui.wx.beans.xml.message.receive.video;

import com.shui.wx.beans.xml.message.receive.base.WxBaseReceiveMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信小视频
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxReceiveShortVideoMessage extends WxBaseReceiveMessage {
    private static final long serialVersionUID = -5059350165312298507L;

    @XStreamAlias("MediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String mediaId;

    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String thumbMediaId;

    @XStreamAlias("MsgId")
    private String msgId;
}
