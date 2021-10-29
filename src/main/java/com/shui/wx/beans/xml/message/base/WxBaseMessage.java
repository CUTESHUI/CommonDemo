package com.shui.wx.beans.xml.message.base;

import com.shui.wx.beans.base.BaseBean;
import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信基础XML
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxBaseMessage extends BaseBean {
    private static final long serialVersionUID = -1512495051521057688L;

    @XStreamAlias("ToUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String toUserName;

    @XStreamAlias("FromUserName")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String fromUserName;

}
