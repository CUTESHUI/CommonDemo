package com.shui.wx.beans.json.mass.openid;

import com.shui.wx.beans.json.base.BaseJsonBean;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 存储MediaId
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassOpenIdMediaId extends BaseJsonBean {
    private static final long serialVersionUID = 6433616473353063645L;
    //用于群发的消息的media_id
    private String media_id;
}
