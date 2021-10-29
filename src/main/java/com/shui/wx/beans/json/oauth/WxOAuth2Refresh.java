package com.shui.wx.beans.json.oauth;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 刷新AccessToken
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxOAuth2Refresh extends BaseJsonBean {
    private static final long serialVersionUID = -3148818563061044292L;
    private String appid;
    private String grant_type = "refresh_token";
    private String refresh_token;

    @Override
    public Map<String, Object> toMap() {
        return super.toMap(this);
    }
}
