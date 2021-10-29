package com.shui.wx.beans.json.mass.preview.wxcard;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信预览卡券信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewCardInfo extends BaseJsonBean {
    private static final long serialVersionUID = -8097237889988413034L;
    private String card_id;
    private WxMassPreviewCardInfoExt card_ext;
}
