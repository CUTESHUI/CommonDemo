package com.shui.wx.beans.json.analysis.resp.news.article.total;

import com.shui.wx.beans.json.analysis.resp.news.WxNewsMsgIdAnalysisResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取图文群发总数据详情
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxNewsArticleTotalDetailsAnalysisResp extends WxNewsMsgIdAnalysisResp {
    private static final long serialVersionUID = -3874672488825117412L;

    private List<WxNewsArticleTotalDetailAnalysisResp> details;
}
