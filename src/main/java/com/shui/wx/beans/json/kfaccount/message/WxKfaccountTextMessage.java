package com.shui.wx.beans.json.kfaccount.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 发送客服文本消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountTextMessage extends WxKfaccountBaseMessage {
    private static final long serialVersionUID = 7362028372011186478L;

    private WxKfaccountTextMessageItem text;
}
