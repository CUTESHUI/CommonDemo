package com.shui.wx.beans.json.analysis.resp.news.msg.upstream.week;

import com.shui.wx.beans.json.analysis.resp.news.msg.upstream.WxMsgUpstreamAnalysisResp;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 获取消息发送周数据
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamWeekAnalysisResp extends WxMsgUpstreamAnalysisResp {
    private static final long serialVersionUID = -5911425853664458444L;
}
