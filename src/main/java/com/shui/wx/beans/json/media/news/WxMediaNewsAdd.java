package com.shui.wx.beans.json.media.news;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 新增永久素材
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMediaNewsAdd  extends BaseJsonBean {
    private static final long serialVersionUID = -3843998711713184044L;

    private List<WxMediaNewsAddItem> articles;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
