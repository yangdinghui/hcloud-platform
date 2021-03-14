package hcloud.platform.modules.sendmail.service.impl;

import com.alibaba.fastjson.JSONObject;
import hcloud.platform.base.excetion.BusinessException;
import hcloud.platform.modules.sendmail.SendMail;
import hcloud.platform.modules.sendmail.dao.MailContentDao;
import hcloud.platform.modules.sendmail.dao.MailReceiverConfigDao;
import hcloud.platform.modules.sendmail.dao.MailSchedulingDao;
import hcloud.platform.modules.sendmail.model.MailContent;
import hcloud.platform.modules.sendmail.model.MailReceiverConfig;
import hcloud.platform.modules.sendmail.model.MailScheduling;
import hcloud.platform.modules.sendmail.service.MailService;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.Sqls;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class MailServiceImpl implements MailService {

    private MailContentDao mailContentDao;
    private MailReceiverConfigDao mailReceiverConfigDao;
    private SendMail sendMessage;
    private MailSchedulingDao mailSchedulingDao;


    public MailServiceImpl(MailSchedulingDao mailSchedulingDao, SendMail sendMessage, MailContentDao mailContentDao, MailReceiverConfigDao mailReceiverConfigDao) {
        this.sendMessage = sendMessage;
        this.mailContentDao = mailContentDao;
        this.mailReceiverConfigDao = mailReceiverConfigDao;
        this.mailSchedulingDao = mailSchedulingDao;
    }

    @Override
    public boolean schedule(MailScheduling scheduling) {
        List<MailScheduling> mailSchedulingList = mailSchedulingDao.selectAll();
        for (MailScheduling mailScheduling : mailSchedulingList) {
            Example example = Example.builder(MailReceiverConfig.class)
                    .andWhere(Sqls.custom()
                            .andEqualTo("recipientName", scheduling.getRecipientName()))
                    .build();
            MailReceiverConfig mailReceiverConfig = mailReceiverConfigDao.selectOneByExample(example);
            if (mailReceiverConfig == null) {
                throw new BusinessException(201, "抱歉，系统尚未收录该对象邮箱!");
            }
            if (Objects.equals(mailScheduling.getRecipientName(), scheduling.getRecipientName())) {
                scheduling.setCreateDate(new Date());
                scheduling.setScheduleCode(mailScheduling.getScheduleCode());
                return mailSchedulingDao.updateByPrimaryKeySelective(scheduling) > 0;
            }
            mailSchedulingDao.deleteByPrimaryKey(mailScheduling.getScheduleCode());
        }
        scheduling.setCreateDate(new Date());
        scheduling.setScheduleCode(UUID.randomUUID().toString());
        return mailSchedulingDao.insertSelective(scheduling) > 0;
    }

    @Override
    public boolean configMail(MailReceiverConfig config) {
        Example example = Example.builder(MailReceiverConfig.class)
                .andWhere(Sqls.custom()
                        .andEqualTo("recipientMail", config.getRecipientMail()))
                .build();
        MailReceiverConfig mailReceiverConfig = mailReceiverConfigDao.selectOneByExample(example);
        if (mailReceiverConfig != null) {
            throw new BusinessException(201, "邮箱已存在!");
        }
        config.setConfigCode(UUID.randomUUID().toString());
        config.setCreateDate(new Date());
        return mailReceiverConfigDao.insertSelective(config) > 0;
    }

    @Override
    public boolean sendMail() {
        List<MailScheduling> mailSchedulingList = mailSchedulingDao.selectAll();
        MailScheduling mailScheduling = mailSchedulingList.get(0);
        Example example = Example.builder(MailReceiverConfig.class)
                .andWhere(Sqls.custom()
                        .andEqualTo("recipientName", mailScheduling.getRecipientName()))
                .build();
        MailReceiverConfig mailReceiverConfig = mailReceiverConfigDao.selectOneByExample(example);
        String recipientMail = mailReceiverConfig.getRecipientMail();
        String[] mailArr = {};
        if (recipientMail.contains(",")) {
            mailArr = recipientMail.split("\\,");
        } else {
            mailArr = new String[]{recipientMail};
        }
        //A-auto自动,例如:彩虹屁;B-自定义
        String type = mailScheduling.getType();
        String msg = SendMail.getOneS();
        if ("B".equals(type)) {
            msg = mailScheduling.getContent();
        }
        msg += "【欢迎使用server酱oo祝您无BUG开发oo】";
        boolean res = sendMessage.sendMessage(mailScheduling.getMailTitle(), msg, mailReceiverConfig.getSenderMail(), mailArr);
        if (res) {
            mailScheduling.setContent(msg);
            record(mailReceiverConfig, mailScheduling);
        }
        return res;
    }

    @Override
    public JSONObject getInfo() {
        List<MailScheduling> mailSchedulingList = mailSchedulingDao.selectAll();
        List<MailReceiverConfig> receiverConfigList = mailReceiverConfigDao.selectAll();
        List<MailContent> mailContentList = mailContentDao.selectAll();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("schedule", mailSchedulingList);
        jsonObject.put("config", receiverConfigList);
        jsonObject.put("content", mailContentList);
        return jsonObject;
    }

    private void record(MailReceiverConfig mailReceiverConfig, MailScheduling mailScheduling) {
        MailContent content = new MailContent();
        content.setMailCode(UUID.randomUUID().toString());
        content.setTitle(mailScheduling.getMailTitle());
        content.setContent(mailScheduling.getContent());
        content.setReceiver(mailReceiverConfig.getRecipientMail());
        content.setSender(mailReceiverConfig.getSenderMail());
        content.setSendDate(new Date());
        mailContentDao.insertSelective(content);
    }

}
