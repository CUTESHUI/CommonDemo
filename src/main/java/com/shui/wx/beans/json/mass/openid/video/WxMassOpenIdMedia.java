package com.shui.wx.beans.json.mass.openid.video;

import com.shui.wx.beans.json.mass.openid.WxMassOpenIdMediaId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 视频的微信多媒体封装
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassOpenIdMedia extends WxMassOpenIdMediaId {
    private static final long serialVersionUID = 2429427167064400749L;
    private String title;
    private String description;
}
