package com.shui.wx.beans.json.analysis.resp.news.msg.upstream.hour;

import com.shui.wx.beans.json.analysis.resp.news.msg.upstream.WxMsgUpstreamItemAnalysisResp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 获取消息分送分时数据条目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMsgUpstreamHourItemAnalysisResp extends WxMsgUpstreamItemAnalysisResp {
    private static final long serialVersionUID = -4112020731703726313L;
    //数据的小时，包括从000到2300，分别代表的是[000,100)到[2300,2400)，即每日的第1小时和最后1小时
    private Integer ref_hour;
}
