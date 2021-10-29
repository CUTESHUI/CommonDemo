package com.shui.wx.beans.json.kfaccount.message;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 发送客服消息的基础参数类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountBaseMessage extends BaseJsonBean {
    /**
     * 发送给某个微信用户的openid
     */
    private String touser;
    /**
     * 消息类型
     */
    private String msgtype;
}
