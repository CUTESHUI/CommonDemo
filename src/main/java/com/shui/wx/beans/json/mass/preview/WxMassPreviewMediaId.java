package com.shui.wx.beans.json.mass.preview;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信多媒体Id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewMediaId extends BaseJsonBean {
    private static final long serialVersionUID = 879969248654091388L;

    private String media_id;
}
