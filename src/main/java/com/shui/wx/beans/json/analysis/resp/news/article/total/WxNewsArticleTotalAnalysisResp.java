package com.shui.wx.beans.json.analysis.resp.news.article.total;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取图文群发总数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxNewsArticleTotalAnalysisResp extends WxCode {
    private static final long serialVersionUID = -14608034032845966L;

    private List<WxNewsArticleTotalDetailsAnalysisResp> list;
}
