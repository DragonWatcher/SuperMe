package com.ahav.email.util;


import java.io.File;
import java.util.Properties;

import javax.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;

import com.ahav.email.pojo.Email;
import com.ahav.email.pojo.ReceiveMail;

/**
 * 发送邮件工具类*/




public class EmailUtils {
    public static boolean sendMail(Email sendMail, ReceiveMail receiveMail) {
        boolean result = true;
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        // 设定mail server
        //服务器地址
        mailSender.setHost(sendMail.getServerAddress());
        //协议类型
        mailSender.setProtocol(sendMail.getPortocolType());
        //发件人邮箱
        mailSender.setUsername(sendMail.getSender());
        //发件人密码
        mailSender.setPassword(sendMail.getSenderPwd());
        if (sendMail != null) {
            if (sendMail.getSslPort() != null) {
                mailSender.setPort(sendMail.getSslPort());
            }
        }
        //编码
        mailSender.setDefaultEncoding("UTF-8");
        Properties prop = new Properties();
        // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.ssl.enable", "true");
        prop.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        mailSender.setJavaMailProperties(prop);

        // 建立邮件消息,发送简单邮件和html邮件的区别
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = null;
        try {
            helper = new MimeMessageHelper(message, true);
            helper.setFrom(sendMail.getSender());
            helper.setTo(receiveMail.getReceiver());
            helper.setSubject(receiveMail.getSubject());
            helper.setText(receiveMail.getContent());
            //添加附件
            if (receiveMail.getFiles() != null && receiveMail.getFiles().length > 0) {
                for (String file : receiveMail.getFiles()) {
                    File f = new File(file);
                    helper.addAttachment(f.getName(), f);
                }
            }

            mailSender.send(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
