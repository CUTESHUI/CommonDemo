package com.shui.wx.beans.json.user.tag;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取标签下粉丝列表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagGetListItemResp extends BaseJsonBean {

    private static final long serialVersionUID = -323360640262586511L;
    //粉丝openid列表
    private List<String> openid;
}
