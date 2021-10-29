package com.shui.wx.beans.json.menu;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信的菜单信息
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
@AllArgsConstructor
public class WxMenuBean extends BaseJsonBean {
    private static final long serialVersionUID = -7579267534455759647L;

    private String menuid;

}
