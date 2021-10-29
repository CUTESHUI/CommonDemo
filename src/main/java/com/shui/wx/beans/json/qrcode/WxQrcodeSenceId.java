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
public class WxQrcodeSenceId extends BaseJsonBean {
    private static final long serialVersionUID = -5383560412961988485L;
    //场景值ID，临时二维码时为32位非0整型，永久二维码时最大值为100000（目前参数只支持1--100000）
    private Integer scene_id;
    //场景值ID（字符串形式的ID），字符串类型，长度限制为1到64
    private String scene_str;
}
