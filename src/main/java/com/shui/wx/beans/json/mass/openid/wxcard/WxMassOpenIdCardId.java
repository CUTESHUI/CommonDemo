package com.shui.wx.beans.json.mass.openid.wxcard;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信卡券信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassOpenIdCardId extends BaseJsonBean {
    private static final long serialVersionUID = 2536366285597788463L;
    private String card_id;
}
