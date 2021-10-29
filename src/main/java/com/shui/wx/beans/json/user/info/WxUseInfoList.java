package com.shui.wx.beans.json.user.info;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量获取用户基本信息
 * 开发者可通过该接口来批量获取用户基本信息。最多支持一次拉取100条。
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUseInfoList extends WxCode {
    private static final long serialVersionUID = -2837521031601571307L;
    private List<WxUseInfo> user_info_list;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
