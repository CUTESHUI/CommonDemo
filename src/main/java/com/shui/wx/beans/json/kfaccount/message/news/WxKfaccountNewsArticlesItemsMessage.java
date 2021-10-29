package com.shui.wx.beans.json.kfaccount.message.news;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 图文消息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountNewsArticlesItemsMessage implements Serializable {

    private static final long serialVersionUID = 869313331150899820L;
    //标题
    private String title = "";
    //描述
    private String description = "";
    //页面链接
    private String url = "";
    //图片链接
    private String picurl = "";

}
