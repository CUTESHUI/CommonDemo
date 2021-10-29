package com.shui.wx.beans.json.user.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * {   "openid" : "ocYxcuBt0mRugKZ7tGAHPnUaOW7Y" }
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagRemark extends WxUserTagOpenId {
    private static final long serialVersionUID = -6288228131097872355L;
    private String remark;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
