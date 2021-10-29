package com.shui.wx.beans.json.template.subscribe;

import com.shui.wx.beans.json.base.BaseJsonBean;
import com.shui.wx.beans.json.template.WxTemplateMiniprogramSend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 一次性订阅消息,通过API推送订阅模板消息给到授权微信用户
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxTemplateSubscribe extends BaseJsonBean {
    private static final long serialVersionUID = -6593973766965899235L;

    private String touser;
    private String template_id;
    private String url;
    private WxTemplateMiniprogramSend miniprogram;
    private String scene;
    private String title;
    private WxTemplateContentSubscribe data;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
