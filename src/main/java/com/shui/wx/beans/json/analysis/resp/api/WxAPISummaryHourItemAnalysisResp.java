package com.shui.wx.beans.json.analysis.resp.api;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 获取接口分析分时数据条目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxAPISummaryHourItemAnalysisResp extends WxAPISummaryItemAnalysisResp {

    private static final long serialVersionUID = -7917759819150591430L;
    //数据的小时
    private Integer ref_hour;
}
