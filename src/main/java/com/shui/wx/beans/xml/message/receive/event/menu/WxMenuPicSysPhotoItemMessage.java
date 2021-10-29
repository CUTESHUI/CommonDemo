package com.shui.wx.beans.xml.message.receive.event.menu;

import com.shui.wx.utils.xml.XStreamCDataConverter;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamConverter;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 *
 */
@XStreamAlias("item")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMenuPicSysPhotoItemMessage implements Serializable {

    @XStreamAlias("PicMd5Sum")
    @XStreamConverter(value = XStreamCDataConverter.class)
    private String picMd5Sum;
}
