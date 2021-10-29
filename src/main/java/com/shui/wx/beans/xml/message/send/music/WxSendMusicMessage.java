package com.shui.wx.beans.xml.message.send.music;

import com.shui.wx.beans.xml.message.send.base.WxBaseSendMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 回复音乐消息
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxSendMusicMessage extends WxBaseSendMessage {
    private static final long serialVersionUID = 4189078785982085539L;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType = "music";

    @XStreamAlias("Title")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String title;

    @XStreamAlias("Description")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String description;

    @XStreamAlias("MusicURL")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String musicURL;

    @XStreamAlias("HQMusicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String HQMusicUrl;

    @XStreamAlias("ThumbMediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String thumbMediaId;
}
