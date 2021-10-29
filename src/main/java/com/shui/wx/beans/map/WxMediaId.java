package com.shui.wx.beans.map;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 媒体Id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMediaId extends BaseJsonBean {
    private static final long serialVersionUID = -8798173681687201613L;

    private String media_id;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }

    @Override
    public Map<String, Object> toMap() {
        return super.toMap(this);
    }
}
