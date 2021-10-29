package com.shui.wx.beans.json.mass.preview.news;

import com.shui.wx.beans.json.mass.preview.WxMassPreviewMediaId;
import com.shui.wx.beans.json.mass.preview.WxMassPreviewToUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 预览图文消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewNewsMessage extends WxMassPreviewToUser {
    private static final long serialVersionUID = 5432198632802958877L;
    private WxMassPreviewMediaId mpnews;
    private String msgtype = "mpnews";

    @Override
    public String toString() {
        return super.toString(this);
    }
}
