package com.shui.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MailDTO implements Serializable {

    // 邮件主题
    private String subject;
    // 邮件内容
    private String content;
    // 接收人
    private String[] tos;
}