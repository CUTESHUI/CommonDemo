package com.shui.wx.beans.json.analysis.resp.news.article.user.read;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取图文统计数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxNewsUserReadAnalysisResp extends WxCode {
    private static final long serialVersionUID = 3097111016673380735L;

    private List<WxNewsUserReadItemAnalysisResp> list;
}
