package com.shui.wx.beans.json.analysis.resp.news.msg.upstream.dist;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取消息发送分布数据接口
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamDistAnalysisResp extends WxCode {
    private static final long serialVersionUID = 2206085642349271938L;

    private List<WxMsgUpstreamDistItemAnalysisResp> list;
}
