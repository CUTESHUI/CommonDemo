package com.shui.wx.beans.json.user.tag;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 批量为用户打/取消标签
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxUserTagMember extends WxUserTagId {
    private static final long serialVersionUID = -9066272691031516551L;

    private List<String> openid_list;
}
