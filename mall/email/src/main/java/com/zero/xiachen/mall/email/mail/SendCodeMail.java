package com.zero.xiachen.mall.email.mail;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Component
public class SendCodeMail {
    //邮箱服务
    @Value("${spring.mail.username}")
    private String srcMailAddr;

    @Autowired
    private JavaMailSender mailSender;  //邮件发送服务

    @RabbitListener(queues = "RegisterCode")
    @RabbitHandler
    public void sendRegisterCode(JSONObject data){
        //解开数据
        System.out.println("注册数据内容"+data);
        String email=data.getString("email");
        String code=data.getString("RegisterCode");
        String time=data.getString("time");
        MimeMessage mimeMessage=null;
        MimeMessageHelper helper=null;

        try{
            //发送复杂的邮件
            mimeMessage=mailSender.createMimeMessage();
            //组装
            helper=new MimeMessageHelper(mimeMessage,true);

            //邮件标题
            helper.setSubject("【夏晨商城】 注册验证码");
            helper.setText("<h3>\n" +
                    "\t<span style=\"font-size:16px;\">亲爱的用户：</span> \n" +
                    "</h3>\n" +
                    "<p>\n" +
                    "\t<span style=\"font-size:14px;\">&nbsp;&nbsp;&nbsp;&nbsp;</span><span style=\"font-size:14px;\">&nbsp; <span style=\"font-size:16px;\">&nbsp;&nbsp;您好！您正在进行邮箱验证，本次请求的验证码为：<span style=\"font-size:24px;color:#FFE500;\"> "+code+"</span>,本验证码5分钟内有效，请在5分钟内完成验证。（请勿泄露此验证码）如非本人操作，请忽略该邮件。(这是一封自动发送的邮件，请不要直接回复）</span></span>\n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:16px;color:#000000;\"><span style=\"color:#000000;font-size:16px;background-color:#FFFFFF;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;background-color:#FFFFFF;\">夏晨商城</span></span></span> \n" +
                    "</p>\n" +
                    "<p style=\"text-align:right;\">\n" +
                    "\t<span style=\"background-color:#FFFFFF;font-size:14px;\"><span style=\"color:#FF9900;font-size:18px;\"><span class=\"token string\" style=\"font-family:&quot;font-size:16px;color:#000000;line-height:normal !important;\"><span style=\"font-size:16px;color:#000000;background-color:#FFFFFF;\">"+time+"</span><span style=\"font-size:18px;color:#000000;background-color:#FFFFFF;\"></span></span></span></span> \n" +
                    "</p>",true);
            helper.setTo(email);  //接收邮箱
            helper.setFrom(srcMailAddr);
            try{
                mailSender.send(mimeMessage); //发送邮件
                return;
            } catch (Exception e){
                //邮箱失效或者发送失败
                e.printStackTrace();  //异常打印
                //logger.warn("邮箱失效或者发送失败\n"+e);
                return;
            }
        } catch (MessagingException e){
            //发送失败，服务器繁忙
            e.printStackTrace();  //异常打印
            //logger.warn("发送失败，服务器繁忙");
            return;
        }
    }

    @RabbitListener(queues = "User")
    @RabbitHandler
    public void sendActiveLink(JSONObject data){
        //数据解绑
        String userName=data.getString("username");
        String userId=data.getString("userid");
        String email=data.getString("email");
        try{
            MimeMessage mimeMessage=null;
            MimeMessageHelper helper=null;
            //发送复杂的邮件
            mimeMessage=mailSender.createMimeMessage();
            //组装
            helper=new MimeMessageHelper(mimeMessage,true);
            helper.setSubject("【夏晨商城】 激活邮件");
            helper.setText("<span>尊敬的："+userName+"</span><br><p style=\"font-size:16px;color: #166924;\">&nbsp;您的账户已经创建成功,请于24小时内点击下方的链接激活您的账户！</p>\n" +
                    "   <a href=\"http://localhost:9001/user/active?usercode="+userId+"\">>>http://localhost:9001/user/active?usercode="+userId+">></a>"+
                    "<p style=\"font-size: 22px;text-align: right;\">夏晨商城</p>",true);
            helper.setTo(email);
            helper.setFrom(srcMailAddr);

            try{
                mailSender.send(mimeMessage); //发送邮件
                return;
            } catch (Exception e){
                //邮箱失效或者发送失败
                return;
            }

        } catch (Exception e){
            e.printStackTrace();
        }
        return;
    }
}
