package hcloud.platform.modules.sendmail.controller;

import com.alibaba.fastjson.JSONObject;
import hcloud.platform.base.view.ApiResponse;
import hcloud.platform.modules.sendmail.model.MailReceiverConfig;
import hcloud.platform.modules.sendmail.model.MailScheduling;
import hcloud.platform.modules.sendmail.service.MailService;
import hcloud.platform.modules.sendmail.service.impl.MailServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/schedule/mail")
@Api(value = "send发送邮件", tags = "send发送邮件")
public class MailController {
    private MailService mailService;

    public MailController(MailServiceImpl mailService) {
        this.mailService = mailService;
    }

    /*定时执行任务方法 每天08点20执行该任务*/
    @Scheduled(cron = "0 20 08 * * *")
    public void sendMessageScheduled() {
        mailService.sendMail();
    }

    @PostMapping("/send")
    @ApiOperation(value = "send发送邮件", notes = "send发送邮件")
    public void sendMessage() {
        mailService.sendMail();
    }

    @PostMapping("/config")
    @ApiOperation(value = "邮件配置接口", notes = "邮件配置接口")
    public ApiResponse<String> config(@RequestBody MailReceiverConfig config) {
        boolean res = mailService.configMail(config);
        if (res) {
            return ApiResponse.success("配置成功");
        }
        return ApiResponse.fail("配置失败");
    }

    @PostMapping("/Scheduled")
    @ApiOperation(value = "邮件调度接口", notes = "邮件调度接口")
    public ApiResponse<String> Scheduled(@RequestBody MailScheduling scheduling) {
        boolean res = mailService.schedule(scheduling);
        if (res) {
            return ApiResponse.success("调度成功");
        }
        return ApiResponse.fail("调度失败");
    }

    @PostMapping("/getInfo")
    @ApiOperation(value = "邮件发送信息获取接口", notes = "邮件发送信息获取接口")
    public ApiResponse<JSONObject> getInfo() {
        return ApiResponse.success(mailService.getInfo());
    }

}
