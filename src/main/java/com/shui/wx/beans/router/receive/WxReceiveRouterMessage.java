package com.shui.wx.beans.router.receive;

import com.shui.wx.beans.json.base.BaseJsonBean;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信路由，业务根据此JavaBean做判断转化为具体的JavaBean
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxReceiveRouterMessage extends BaseJsonBean {
    private static final long serialVersionUID = -7407464380592497012L;

    @XStreamAlias("MsgType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String msgType;

    @XStreamAlias("Event")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String event;

    @XStreamAlias("Ticket")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String ticket;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
