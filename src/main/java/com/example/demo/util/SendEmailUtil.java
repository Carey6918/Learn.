package com.example.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class SendEmailUtil {
    @Autowired
    private JavaMailSender javaMailSender;

    public void sendAttachmentsMail(int identifyCode,String email) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("727929807@qq.com");
        helper.setTo(email);
        helper.setSubject("主题：点击此邮件验证——来自呸呸培训");
        helper.setText("您的验证码为：" + identifyCode);
        javaMailSender.send(mimeMessage);
    }
}
