package com.shui.wx.beans.json.analysis.resp.news;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信获取图文消息统计结果基础类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxNewsBaseAnalysisResp extends BaseJsonBean {
    private static final long serialVersionUID = 8060311717449513755L;

    //数据的日期，需在begin_date和end_date之间
    private String ref_date;
}
