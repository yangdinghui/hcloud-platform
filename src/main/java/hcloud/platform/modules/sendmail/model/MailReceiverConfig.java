package hcloud.platform.modules.sendmail.model;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Data
@Table(name = "mail_receiver_config")
public class MailReceiverConfig {
    /**
     * 配置编码
     */
    @Id
    @Column(name = "configCode")
    private String configCode;

    /**
     * 接收人姓名
     */
    @Column(name = "recipientName")
    private String recipientName;

    /**
     * 接收人邮箱
     */
    @Column(name = "recipientMail")
    private String recipientMail;

    /**
     * 创建时间
     */
    @Column(name = "createDate")
    private Date createDate;

    /**
     * 发送内容获取方式:A-auto自动,例如:彩虹屁;B-z自定义
     */
    @Column(name = "type")
    private String type;

    /**
     * 发送者邮箱
     */
    @Column(name = "senderMail")
    private String senderMail;
}