package com.shui.wx.beans.json.analysis.resp.news.article.summary;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取图文群发每日数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxArticleSummaryAnalysisResp extends WxCode {
    private static final long serialVersionUID = -4173165803281880668L;

    private List<WxArticleSummaryItemAnalysisResp> list;

}
