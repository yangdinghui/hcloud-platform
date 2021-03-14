package hcloud.platform.modules.sendmail.dao;

import hcloud.platform.base.mapper.BaseMapper;
import hcloud.platform.modules.sendmail.model.MailContent;
import org.springframework.stereotype.Repository;

@Repository
public interface MailContentDao extends BaseMapper<MailContent> {
}
