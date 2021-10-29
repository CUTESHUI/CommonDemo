package com.shui.wx.beans.xml.message.receive.event.mass;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 事件推送群发结果
 */
@XStreamAlias("CopyrightCheckResult")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassCopyrightCheckResult implements Serializable {
    private static final long serialVersionUID = 2116990278610941290L;

    @XStreamAlias("Count")
    private Integer count;

    @XStreamAlias("CheckState")
    private Integer checkState;

    @XStreamAlias("ResultList")
    private List<WxMassResultListItem> resultList;
}
