package com.shui.wx.beans.json.qrcode;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 二维码
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxQrcodeSence extends BaseJsonBean {
    private static final long serialVersionUID = -8686808941201558254L;

    private WxQrcodeSenceId scene;
}
