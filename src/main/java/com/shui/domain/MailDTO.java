package com.shui.domain;

import lombok.*;

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