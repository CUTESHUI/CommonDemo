package com.shui.wx.beans.json.shorturl;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 生成微信短连接响应的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxShortUrlResp extends WxCode {
    private static final long serialVersionUID = 6181777295682541665L;
    //转化后的短连接
    private String short_url;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
