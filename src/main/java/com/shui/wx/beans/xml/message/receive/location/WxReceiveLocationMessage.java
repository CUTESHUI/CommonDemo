package com.shui.wx.beans.xml.message.receive.location;

import com.shui.wx.beans.xml.message.receive.base.WxBaseReceiveMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 接收地理位置的信息
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxReceiveLocationMessage extends WxBaseReceiveMessage {
    private static final long serialVersionUID = -7702840453462247896L;

    @XStreamAlias("Location_X")
    private Double location_X;

    @XStreamAlias("Location_Y")
    private Double location_Y;

    @XStreamAlias("Scale")
    private Integer scale;

    @XStreamAlias("Label")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String label;

    @XStreamAlias("MsgId")
    private Long msgId;
}
