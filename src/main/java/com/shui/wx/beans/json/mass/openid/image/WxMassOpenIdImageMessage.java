package com.shui.wx.beans.json.mass.openid.image;

import com.shui.wx.beans.json.mass.openid.WxMassOpenIdMediaId;
import com.shui.wx.beans.json.mass.openid.WxMassOpenIdToUser;
import com.shui.wx.utils.common.UUIDUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 群发微信图片
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassOpenIdImageMessage extends WxMassOpenIdToUser {
    private static final long serialVersionUID = 7342743968370851024L;

    private WxMassOpenIdMediaId image;
    private String msgtype = "image";
    //开发者侧群发msgid，长度限制64字节，如不填，则后台默认以群发范围和群发内容的摘要值做为clientmsgid
    private String clientmsgid = UUIDUtils.getUUID();

    @Override
    public String toString() {
        return super.toString(this);
    }
}
