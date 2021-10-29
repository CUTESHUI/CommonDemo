package com.shui.wx.beans.json.analysis.resp.news.article.user.share.hour;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取图文分享转发分时数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxNewsUserShareHourAnalysisResp extends WxCode {
    private static final long serialVersionUID = -2430047659167149460L;

    private List<WxNewsUserShareHourItemAnalysisResp> list;
}
