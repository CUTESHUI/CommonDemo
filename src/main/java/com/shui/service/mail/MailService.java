package com.shui.service.mail;

import com.shui.domain.MailDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import javax.mail.internet.MimeMessage;

@Slf4j
@EnableAsync
@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String fromEmail;

    @Autowired
    private JavaMailSender mailSender;

    /**
     * 发送简单文本文件
     */
    @Async
    public void sendSimpleEmail(MailDTO dto) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(fromEmail);
            message.setTo(dto.getTos());
            message.setSubject(dto.getSubject());
            message.setText(dto.getContent());
            mailSender.send(message);
            log.info("发送简单文本文件-发送成功!");
        } catch (Exception e) {
            log.error("发送简单文本文件-发生异常： ", e.fillInStackTrace());
        }
    }

    /**
     * 发送花哨邮件
     */
    @Async
    public void sendHtmlMail(MailDTO dto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper messageHelper = new MimeMessageHelper(message, true, "utf-8");
            messageHelper.setFrom(fromEmail);
            messageHelper.setTo(dto.getTos());
            messageHelper.setSubject(dto.getSubject());
            messageHelper.setText(dto.getContent(), true);
            mailSender.send(message);
            log.info("发送花哨邮件-发送成功!");
        } catch (Exception e) {
            log.error("发送花哨邮件-发生异常：", e.fillInStackTrace());
        }
    }
}