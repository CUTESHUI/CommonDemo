package com.shui.wx.beans.xml.message.receive.event.menu;

import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 扫码推事件的事件推送条目
 */
@XStreamAlias("ScanCodeInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuScanCodeItemEventMessage implements Serializable {

    private static final long serialVersionUID = -2908270646115392867L;

    @XStreamAlias("ScanType")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanType;

    @XStreamAlias("ScanResult")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String scanResult;
}
