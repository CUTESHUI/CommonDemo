package com.shui.wx.beans.json.kfaccount;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 获取客服信息时返回的微信数据
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxKfaccountInfoList extends BaseJsonBean {
    private static final long serialVersionUID = -5988026383074143696L;
    private List<WxKfaccountInfo> kf_list;
}
