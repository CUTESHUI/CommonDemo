package com.shui.wx.beans.json.oauth;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 检验授权凭证（access_token）是否有效
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxOAuth2Auth extends BaseJsonBean {
    private static final long serialVersionUID = -1546533627000640353L;
    private String access_token;
    private String openid;
}
