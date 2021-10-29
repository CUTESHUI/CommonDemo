package com.shui.wx.beans.json.analysis.resp.news.msg.upstream.hour;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取消息分送分时数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamHourAnalysisResp extends WxCode {
    private List<WxMsgUpstreamHourItemAnalysisResp> list;
}
