package com.shui.wx.beans.json.mass.tag.video;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 *
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassTagVideoUploadResponseMessage extends BaseJsonBean {
    private static final long serialVersionUID = 832294288132768730L;
    private String type = "video";
    private String media_id;
    private Long created_at;
    @Override
    public String toString() {
        return super.toString(this);
    }
}
