package com.shui.wx.beans.json.mass.preview.wxcard;

import com.shui.wx.beans.json.mass.preview.WxMassPreviewToUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 预览微信卡券
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewCardMessage extends WxMassPreviewToUser {
    private static final long serialVersionUID = 6459395097916786800L;
    private WxMassPreviewCardInfo wxcard;
    private String msgtype = "wxcard";

    @Override
    public String toString() {
        return super.toString(this);
    }
}
