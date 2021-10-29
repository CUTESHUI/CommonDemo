package com.shui.wx.beans.json.analysis.resp.user;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信用户分析接口返回数据基础类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserBaseAnalysisResp extends BaseJsonBean {
    private static final long serialVersionUID = -4934494314532456526L;
    //数据的日期
    private String ref_date;
}
