package hcloud.platform.modules.sendmail;

import hcloud.platform.base.excetion.BusinessException;
import hcloud.platform.modules.server.controller.TestServer;
import hcloud.platform.modules.server.entity.Message;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;

@Component
public class SendMail {

    @Autowired
    private TestServer testServer;

    @Autowired
    private JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String from;
    @Value("${she.mail}")
    private String[] sheMail;

    public boolean sendMessage(String subject, String message, String fromMail, String[] mailArr) {
        boolean flag = true;
        Message msg = new Message();
        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
            helper.setFrom(fromMail);//发送者邮件邮箱
            helper.setTo(mailArr);//收邮件者邮箱
            helper.setSubject(subject);//发件主题
            helper.setText(message);//发件内容
            mailSender.send(helper.getMimeMessage());//发送邮件
            msg.setText("邮件发送成功！");
            msg.setDesp("邮件内容：" + message);
            testServer.pushPost(msg);
        } catch (MessagingException e) {
            flag = false;
            e.printStackTrace();
            msg.setText("邮件发送错误日志");
            msg.setDesp(e.toString());
            testServer.pushPost(msg);
        }
        return flag;
    }

    /**
     * 远程获取要发送的信息
     */
    public static String getOneS() {
        try {
            //创建客户端对象
            HttpClient client = HttpClients.createDefault();
            /*彩虹屁生产地址*/
            HttpGet get = new HttpGet("https://chp.shadiao.app/api.php");
            //发起请求，接收响应对象
            HttpResponse response = client.execute(get);
            //获取响应体，响应数据是一种基于HTTP协议标准字符串的对象
            //响应体和响应头，都是封装HTTP协议数据。直接使用可能出现乱码或解析错误
            HttpEntity entity = response.getEntity();
            //通过HTTP实体工具类，转换响应体数据
            String responseString = EntityUtils.toString(entity, "utf-8");
            return responseString;
        } catch (IOException e) {
            throw new BusinessException(201, "网站获取句子失败");
        }
    }
}
