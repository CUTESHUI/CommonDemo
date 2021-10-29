package com.shui.wx.beans.json.template.subscribe;

import com.shui.wx.beans.json.base.BaseJsonBean;
import com.shui.wx.beans.json.template.WxTemplateDataItemSend;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxTemplateContentSubscribe extends BaseJsonBean {
    private static final long serialVersionUID = 2613266155354042128L;
    private WxTemplateDataItemSend content;
}
