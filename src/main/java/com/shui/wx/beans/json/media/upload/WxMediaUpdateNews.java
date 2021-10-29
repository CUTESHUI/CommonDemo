package com.shui.wx.beans.json.media.upload;

import com.shui.wx.beans.json.media.news.WxMediaNewsAddItem;
import com.shui.wx.beans.map.WxMediaId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 修改永久图文素材
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMediaUpdateNews extends WxMediaId {
    private static final long serialVersionUID = -168147333338720239L;
    private Integer index = 0;
    private WxMediaNewsAddItem articles;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
