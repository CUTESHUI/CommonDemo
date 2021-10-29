package com.shui.wx.beans.json.shorturl;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 生成微信短连接提交的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxShortUrl extends BaseJsonBean {
    private static final long serialVersionUID = -7175795236545788466L;
    //此处填long2short，代表长链接转短链接
    private String action = "long2short";
    //	需要转换的长链接，支持http://、https://、weixin://wxpay 格式的url
    private String long_url;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
