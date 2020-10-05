package com.zero.xiachen.mall.email.mail;

import com.alibaba.fastjson.JSONObject;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class sendOrderMail {

    //邮箱服务
    @Value("${spring.mail.username}")
    private String srcMailAddr;

    @Autowired
    private JavaMailSender mailSender;  //邮件发送服务

    @RabbitListener(queues = "order")
    @RabbitHandler
    public void sendOrderMail(JSONObject data){
        //数据解绑
        String emial=data.getString("email");
        String orderCode=data.getString("ordercode");
        String time=data.getString("time");
        String userName=data.getString("username");
        String number=data.getString("number");
        String amount=data.getString("amount");

        MimeMessage mimeMessage=null;
        MimeMessageHelper helper=null;

        try{
            //发送复杂的邮件
            mimeMessage=mailSender.createMimeMessage();
            //组装
            helper=new MimeMessageHelper(mimeMessage,true);
            //邮件内容
            helper.setSubject("【夏晨商城】 订单提醒服务");
            Date date=new Date();
            SimpleDateFormat ft=new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
            String sendTime=ft.format(date);
            helper.setText("<span style=\"color:aqua;font-size: 35px;\">尊敬的"+userName+"：</span>\n" +
                    "    <p> &nbsp&nbsp&nbsp&nbsp&nbsp&nbsp您的订单："+orderCode+"已于"+time+"提交。订单内商品共"+number+"种，合计"+amount+"元。订单详情请登录 <a href=\"http://localhost:9001/page/member\">会员中心</a>查看。</p>\n" +
                    "    <h3 style=\"float: right;font-size: 35px;\">夏晨商城</h3>\n" +
                    "    <h6 style=\"float:right;\">"+sendTime+"</h6>",true);
            helper.setTo(emial);
            helper.setFrom(srcMailAddr);
            try{
                mailSender.send(mimeMessage); //发送邮件
                return;
            } catch (MailException e){
                e.printStackTrace();  //异常打印
                return;
            }
        }catch (Exception e) {
            e.printStackTrace();
            return;
        }
    }
}
