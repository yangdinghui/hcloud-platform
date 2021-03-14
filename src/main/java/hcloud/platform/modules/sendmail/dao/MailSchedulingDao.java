package hcloud.platform.modules.sendmail.dao;

import hcloud.platform.base.mapper.BaseMapper;
import hcloud.platform.modules.sendmail.model.MailScheduling;
import org.springframework.stereotype.Repository;

@Repository
public interface MailSchedulingDao extends BaseMapper<MailScheduling> {
}
