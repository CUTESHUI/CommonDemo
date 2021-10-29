package com.shui.wx.beans.json.user.tag;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信标签名称
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagName extends BaseJsonBean {
    private static final long serialVersionUID = -4080946666089667092L;
    private String name;
    private Integer id;
    private Integer count;
}
