package com.shui.wx.beans.json.analysis.resp.api;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取接口分析数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxAPISummaryAnalysisResp extends WxCode {
    private static final long serialVersionUID = 4444699391453793747L;

    private List<WxAPISummaryItemAnalysisResp> list;
}
