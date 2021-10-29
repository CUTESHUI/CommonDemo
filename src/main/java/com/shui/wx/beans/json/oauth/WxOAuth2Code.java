package com.shui.wx.beans.json.oauth;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 通过code换取网页授权access_token
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxOAuth2Code extends BaseJsonBean {
    private static final long serialVersionUID = 7225479056169724272L;
    private String code;
    private String grant_type = "authorization_code";

    @Override
    public Map<String, Object> toMap() {
        return super.toMap(this);
    }
}
