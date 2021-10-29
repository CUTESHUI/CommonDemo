package com.shui.wx.beans.json.qrcode;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 请求临时/永久二维码的返回结果
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxQrcodeResp extends WxCode {
    private static final long serialVersionUID = 6648954841443051956L;
    //获取的二维码ticket，凭借此ticket可以在有效时间内换取二维码。
    private String ticket;
    //该二维码有效时间，以秒为单位。 最大不超过2592000（即30天）。
    private Integer expire_seconds;
    //二维码图片解析后的地址，开发者可根据该地址自行生成需要的二维码图片
    private String url;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
