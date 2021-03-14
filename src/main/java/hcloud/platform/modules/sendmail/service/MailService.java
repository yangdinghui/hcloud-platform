package hcloud.platform.modules.sendmail.service;

import com.alibaba.fastjson.JSONObject;
import hcloud.platform.modules.sendmail.model.MailReceiverConfig;
import hcloud.platform.modules.sendmail.model.MailScheduling;

public interface MailService {

    boolean schedule(MailScheduling scheduling);

    boolean configMail(MailReceiverConfig config);

    boolean sendMail();

    JSONObject getInfo();
}
