package com.shui.wx.beans.json.mass.tag.video;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 上传视频素材
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassTagVideoUploadMessage extends BaseJsonBean {
    private static final long serialVersionUID = 4744414632367354000L;
    //此处media_id需通过基础支持中的上传下载多媒体文件来得到
    private String media_id;
    private String title;
    private String description;

    @Override
    public String toString() {
        return super.toString(this);
    }
}
