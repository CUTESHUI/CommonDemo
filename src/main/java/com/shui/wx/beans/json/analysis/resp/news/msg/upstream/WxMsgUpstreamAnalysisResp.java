package com.shui.wx.beans.json.analysis.resp.news.msg.upstream;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取消息发送概况数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamAnalysisResp extends WxCode {
    private static final long serialVersionUID = -8978779984866252636L;

    private List<WxMsgUpstreamItemAnalysisResp> list;
}
