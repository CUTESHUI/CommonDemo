package com.shui.wx.beans.json.mass.preview;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信预览，towxname和touser同时赋值时，以towxname优先
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewToUser extends BaseJsonBean {
    private static final long serialVersionUID = -2544620438120793498L;
    //微信的openId
    private String touser;
    //微信号
    private String towxname;

}
