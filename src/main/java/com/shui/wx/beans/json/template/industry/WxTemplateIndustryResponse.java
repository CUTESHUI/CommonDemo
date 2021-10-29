package com.shui.wx.beans.json.template.industry;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 获取设置的行业信息返回数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxTemplateIndustryResponse extends WxCode {
    private static final long serialVersionUID = 2050906079508392483L;

    private WxTemplateIndustryItemResponse primary_industry;

    private WxTemplateIndustryItemResponse secondary_industry;
}
