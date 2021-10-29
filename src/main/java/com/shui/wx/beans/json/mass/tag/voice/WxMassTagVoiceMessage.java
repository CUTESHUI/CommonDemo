package com.shui.wx.beans.json.mass.tag.voice;

import com.shui.wx.beans.json.base.BaseJsonBean;
import com.shui.wx.beans.json.mass.tag.WxMassTagFilter;
import com.shui.wx.beans.json.mass.tag.WxMassTagMediaId;
import com.shui.wx.utils.common.UUIDUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 群发语音消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassTagVoiceMessage extends BaseJsonBean {
    private static final long serialVersionUID = -2423638373477748527L;
    private WxMassTagFilter filter;
    private WxMassTagMediaId voice;
    private String msgtype = "voice";
    //开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
    private String clientmsgid = UUIDUtils.getUUID();

    @Override
    public String toString() {
        return super.toString(this);
    }
}
