package com.shui.wx.beans.json.user.tag;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 创建用户标签的响应数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagCreateResp extends WxCode {
    private static final long serialVersionUID = 7744011799799363940L;

    private WxUserTagName tag;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
