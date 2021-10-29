package com.shui.wx.beans.json.mass.tag.text;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 文本内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassTagTextContent extends BaseJsonBean {
    private static final long serialVersionUID = -1014595275249579161L;
    private String content;
}
