package com.shui.wx.beans.json.template;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信模板id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxTemplateId extends BaseJsonBean {
    private static final long serialVersionUID = 6580132554742967614L;
    private String template_id;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
