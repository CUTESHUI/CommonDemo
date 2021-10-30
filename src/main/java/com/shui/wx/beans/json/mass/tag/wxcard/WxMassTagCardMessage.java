package com.shui.wx.beans.json.mass.tag.wxcard;

import com.shui.wx.beans.json.base.BaseJsonBean;
import com.shui.wx.beans.json.mass.tag.WxMassTagFilter;
import com.shui.wx.utils.common.UUIDUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 群发卡券消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassTagCardMessage extends BaseJsonBean {
    private static final long serialVersionUID = 2571829516348374902L;
    private WxMassTagFilter filter;
    private WxMassTagCardId wxcard;
    private String msgtype = "wxcard";
    //开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
    private String clientmsgid = UUIDUtils.getUUID();

    @Override
    public String toString() {
        return super.toString(this);
    }
}