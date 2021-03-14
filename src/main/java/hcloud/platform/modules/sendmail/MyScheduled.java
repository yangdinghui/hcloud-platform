package hcloud.platform.modules.sendmail;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/mail")
@Api(value = "send发送邮件", tags = "send发送邮件")
//@Component
public class MyScheduled {
    @Autowired
    private SendMail sendMessage;

    /*定时执行任务方法 每天17点02执行该任务*/
//    @Scheduled(cron = "0 02 17 * * *")
    @PostMapping("/send")
    @ApiOperation(value = "send发送邮件", notes = "send发送邮件")
    public void sendMessage() {
        String message = sendMessage.getOneS();
//        sendMessage.sendMessage("来自【杨丁辉】的测试消息（彩虹屁~~~）", message);
    }

//    @Scheduled(cron = "0 20 08 * * *")
//    public void sendMessageScheduled() {
//        String message = sendMessage.getOneS();
//        sendMessage.sendMessage("来自【杨丁辉】的测试消息（彩虹屁~~~）", message);
//    }
}
