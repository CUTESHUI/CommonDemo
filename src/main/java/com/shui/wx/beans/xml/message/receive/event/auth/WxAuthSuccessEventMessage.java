package com.shui.wx.beans.xml.message.receive.event.auth;

import com.shui.wx.beans.xml.message.receive.event.base.WxBaseReceiveEventMessage;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 资质认证成功（此时立即获得接口权限）/ 名称认证成功（即命名成功） / 年审通知 / 认证过期失效通知审通知
 */
@XStreamAlias("xml")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxAuthSuccessEventMessage extends WxBaseReceiveEventMessage {
    private static final long serialVersionUID = -1676950896516704499L;

    //有效期 (整形)，指的是时间戳，将于该时间戳认证过期
    @XStreamAlias("ExpiredTime")
    private Long expiredTime;
}
