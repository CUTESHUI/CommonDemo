package com.shui.wx.beans.json.mass.openid.text;

import com.shui.wx.beans.json.mass.openid.WxMassOpenIdToUser;
import com.shui.wx.utils.common.UUIDUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 根据openId 发送文本消息的类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassOpenIdTextMessage extends WxMassOpenIdToUser {
    private static final long serialVersionUID = -6961278727318084027L;
    private String msgtype = "text";
    private WxMassOpenIdContentMessage text;
    //开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
    private String clientmsgid = UUIDUtils.getUUID();

    @Override
    public String toString() {
        return super.toString(this);
    }
}
