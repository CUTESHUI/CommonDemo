package com.shui.wx.beans.json.mass.preview.wxcard;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 预览微信卡券扩展
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewCardInfoExt extends BaseJsonBean {
    private static final long serialVersionUID = 6381744720414597264L;
    private String code;
    private String openid;
    private Long timestamp;
    private String signature;
}
