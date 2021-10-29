package com.shui.wx.beans.json.analysis.resp.user;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取用户增减数据返回的数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserSummaryAnalysisResp extends WxCode {
    private static final long serialVersionUID = 8560830443217095237L;

    private List<WxUserSummaryAnalysisItemResp> list;

    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
