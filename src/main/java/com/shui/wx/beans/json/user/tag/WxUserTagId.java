package com.shui.wx.beans.json.user.tag;

import com.shui.wx.beans.json.code.WxCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 微信标签id
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagId extends WxCode {
    private static final long serialVersionUID = 5682324248213594313L;
    private Integer tagid;
    private List<Integer> tagid_list;
    @Override
    public String toJsonString() {
        return super.toJsonString(this);
    }
}
