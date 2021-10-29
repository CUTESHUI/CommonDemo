package com.shui.wx.beans.xml.message.receive.event.mass;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 事件推送群发结果
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = 8794208455004124005L;

    @XStreamAlias("MsgID")
    private Long msgId;

    @XStreamAlias("Status")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String status;

    @XStreamAlias("TotalCount")
    private Integer totalCount;

    @XStreamAlias("FilterCount")
    private Integer filterCount;

    @XStreamAlias("SentCount")
    private Integer sentCount;

    @XStreamAlias("ErrorCount")
    private Integer errorCount;

    @XStreamAlias("CopyrightCheckResult")
    private WxMassCopyrightCheckResult copyrightCheckResult;


    public String toXmlString() {
        return super.toXmlString(this);
    }

}
