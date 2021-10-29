package com.shui.wx.beans.json.kfaccount.message.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 图文消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountNewsArticlesMessage implements Serializable {
    private static final long serialVersionUID = -7389010408589869700L;

    //文章列表
    private List<WxKfaccountNewsArticlesItemsMessage> articles;
}
