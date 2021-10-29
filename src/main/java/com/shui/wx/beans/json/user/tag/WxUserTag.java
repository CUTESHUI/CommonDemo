package com.shui.wx.beans.json.user.tag;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信用户标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTag extends BaseJsonBean {
    private static final long serialVersionUID = 7744011799799363940L;

    private WxUserTagName tag;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
