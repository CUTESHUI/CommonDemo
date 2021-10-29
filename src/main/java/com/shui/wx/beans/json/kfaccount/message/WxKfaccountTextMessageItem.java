package com.shui.wx.beans.json.kfaccount.message;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 微信客服文本消息条目
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountTextMessageItem extends BaseJsonBean {
    private static final long serialVersionUID = -2203162836640902590L;
    /**
     * 发送的微信文本消息内容
     */
    private String content;
}
