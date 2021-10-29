package com.shui.wx.beans.xml.message.receive.image;

import com.shui.wx.beans.xml.message.receive.base.WxBaseReceiveMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * @Author: liuyazhuang
 * @Date: 2018/7/18 17:38
 * @Description: 图片消息
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxReceiveImageMessage extends WxBaseReceiveMessage {
    private static final long serialVersionUID = -5278257332203671364L;

    @XStreamAlias("PicUrl")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String picUrl;

    @XStreamAlias("MediaId")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String mediaId;

    @XStreamAlias("MsgId")
    private Long msgId;
}
