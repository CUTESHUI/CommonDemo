package com.shui.wx.beans.json.token;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 微信的AccessToken
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxAccessToken extends BaseJsonBean {
    private static final long serialVersionUID = -2954185406720903761L;
    //解析的Access_token
    private String access_token;

    //解析的过期时间
    private int expires_in;

    @Override
    public String toString() {
        return super.toString(this);
    }
}
