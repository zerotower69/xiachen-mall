package com.zero.xiachen.mall.email.service.Impl;

import org.apache.commons.net.smtp.SMTPClient;
import org.apache.commons.net.smtp.SMTPReply;
import com.zero.xiachen.mall.email.service.MailService;
import org.springframework.stereotype.Service;
import org.xbill.DNS.Lookup;
import org.xbill.DNS.Record;
import org.xbill.DNS.Type;

import javax.servlet.http.HttpServletResponse;

@Service("MailService")
public class MailServiceImpl implements MailService {
    @Override
    public void checkEmail(String email, HttpServletResponse response) {
        String host = "";
        String hostName = email.split("@")[1];
        System.out.println("打印测试 "+email);
        //Record: A generic DNS resource record. The specific record types
        //extend this class. A record contains a name, type, class, ttl, and rdata.
        Record[] result = null;
        SMTPClient client = new SMTPClient();

        try {
            // 查找DNS缓存服务器上为MX类型的缓存域名信息
            Lookup lookup = new Lookup(hostName, Type.MX);
            lookup.run();
            if (lookup.getResult() != lookup.SUCCESSFUL) {
                //查找失败
                //logger.error("邮箱(" + email + ")校验未通过，未找到对应的MX记录！");
                response.setStatus(204);   //400状态码
                return;
            } else {
                //找到了
                result = lookup.getAnswers();
            }
            //尝试和SMTP邮箱服务器建立Socket连接
            client.setDefaultTimeout(6*1000);  //一次请求的最大时长是6s
            for (Record item : result
            ) {
                host = item.getAdditionalName().toString();
                //logger.info("SMTPClient try connect to " + host);
                //开始建立连接
                client.connect(host);
                //查看对应的响应码
                if (!SMTPReply.isPositiveCompletion(client.getReplyCode())) {
                    //错误码，断开连接
                    client.disconnect();
                    continue;
                } else {
                    //正确码
                    //logger.info("找到MX记录:" + hostName);
                    //logger.info("建立链接成功" + hostName);
                    response.setStatus(200);
                    return;
                }
            }
            response.setStatus(204);
            return;
            //日志信息

        } catch (Exception e) {
            //logger.info("发送验证码发生错误！");
            e.printStackTrace();
            response.setStatus(205);
            return;
        }
    }
}
