package com.shui.wx.beans.json.media.upload;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 上传图文消息请求体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMediaUploadNews extends BaseJsonBean {
    //图文消息列表
    private List<WxMediaUploadNewsItem> articles;

    @Override
    public String toString() {
        return super.toString(this);
    }
}
