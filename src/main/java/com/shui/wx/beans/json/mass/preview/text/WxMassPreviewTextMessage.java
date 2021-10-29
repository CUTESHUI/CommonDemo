package com.shui.wx.beans.json.mass.preview.text;

import com.shui.wx.beans.json.mass.preview.WxMassPreviewToUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 预览文本消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewTextMessage extends WxMassPreviewToUser {
    private static final long serialVersionUID = 1719042384066893332L;
    private WxMassPreviewContentMessage text;
    private String msgtype = "text";

    @Override
    public String toString() {
        return super.toString(this);
    }
}
