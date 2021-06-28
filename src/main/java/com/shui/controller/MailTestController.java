package com.shui.controller;

import com.shui.domain.MailDTO;
import com.shui.service.mail.MailService;
import com.shui.utils.SpringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author shui
 */
@Api(tags = "mail测试")
@RestController
@RequestMapping("/mail")
public class MailTestController {

    @Autowired
    private MailService mailService;

    @PostMapping("sendSimpleEmail")
    @ApiOperation("异步发送简单邮件")
    public void getAllUser(@RequestBody MailDTO mailDTO) {
        this.mailService.sendSimpleEmail(mailDTO);
    }
}
