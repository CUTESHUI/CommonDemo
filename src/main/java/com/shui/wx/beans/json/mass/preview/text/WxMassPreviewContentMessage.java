package com.shui.wx.beans.json.mass.preview.text;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 内容
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class WxMassPreviewContentMessage implements Serializable {
    private static final long serialVersionUID = 6974681925222992691L;
    private String content;
}
