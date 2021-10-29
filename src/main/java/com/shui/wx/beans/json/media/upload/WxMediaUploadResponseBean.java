package com.shui.wx.beans.json.media.upload;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 上传多媒体文件返回的结果数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMediaUploadResponseBean extends BaseJsonBean {
    private static final long serialVersionUID = 8219812894814406429L;

    private String type;
    private String media_id;
    private Long create_at;

}
