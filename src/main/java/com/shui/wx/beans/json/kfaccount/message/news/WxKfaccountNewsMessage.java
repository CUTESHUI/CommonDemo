package com.shui.wx.beans.json.kfaccount.message.news;

import com.shui.wx.beans.json.kfaccount.message.WxKfaccountBaseMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 图文消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountNewsMessage extends WxKfaccountBaseMessage {
    private static final long serialVersionUID = 1773048341897815048L;

    private WxKfaccountNewsArticlesMessage news;
}
