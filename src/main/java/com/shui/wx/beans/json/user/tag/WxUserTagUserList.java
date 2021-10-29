package com.shui.wx.beans.json.user.tag;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量获取用户基本信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagUserList extends BaseJsonBean {
    private static final long serialVersionUID = 2869743718082249911L;

    private List<WxUserTagLang> user_list;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
