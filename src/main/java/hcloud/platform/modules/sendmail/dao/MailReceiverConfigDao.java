package hcloud.platform.modules.sendmail.dao;

import hcloud.platform.base.mapper.BaseMapper;
import hcloud.platform.modules.sendmail.model.MailReceiverConfig;
import org.springframework.stereotype.Repository;

@Repository
public interface MailReceiverConfigDao extends BaseMapper<MailReceiverConfig> {
}
