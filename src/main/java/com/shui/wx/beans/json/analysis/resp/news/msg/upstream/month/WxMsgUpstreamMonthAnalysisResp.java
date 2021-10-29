package com.shui.wx.beans.json.analysis.resp.news.msg.upstream.month;

import com.shui.wx.beans.json.analysis.resp.news.msg.upstream.WxMsgUpstreamAnalysisResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取消息发送月数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamMonthAnalysisResp extends WxMsgUpstreamAnalysisResp {
    private static final long serialVersionUID = -379556154713007241L;
}
