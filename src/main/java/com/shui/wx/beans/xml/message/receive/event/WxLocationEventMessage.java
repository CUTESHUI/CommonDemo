package com.shui.wx.beans.xml.message.receive.event;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 上报地理位置信息
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxLocationEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = -5956367691437971176L;

    @XStreamAlias("Latitude")
    private Double latitude;

    @XStreamAlias("Longitude")
    private Double longitude;

    @XStreamAlias("Precision")
    private Double precision;
}
