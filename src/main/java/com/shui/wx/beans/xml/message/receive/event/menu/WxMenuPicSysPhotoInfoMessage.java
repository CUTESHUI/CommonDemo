package com.shui.wx.beans.xml.message.receive.event.menu;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 *
 */
@XStreamAlias("SendPicsInfo")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuPicSysPhotoInfoMessage implements Serializable {
    private static final long serialVersionUID = -7753856734346170701L;

    @XStreamAlias("Count")
    private Integer count;

    @XStreamAlias("PicList")
    private List<WxMenuPicSysPhotoItemMessage> picList;
}
