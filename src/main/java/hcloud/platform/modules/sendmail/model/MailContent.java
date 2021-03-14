package hcloud.platform.modules.sendmail.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "mail_content")
public class MailContent {
    /**
     * 邮件发送编码
     */
    @Id
    @Column(name = "mailCode")
    private String mailCode;

    /**
     * 邮件标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 发送人
     */
    @Column(name = "sender")
    private String sender;

    /**
     * 接收人
     */
    @Column(name = "receiver")
    private String receiver;

    /**
     * 发送时间
     */
    @Column(name = "sendDate")
    private Date sendDate;

    /**
     * 邮件内容
     */
    @Column(name = "content")
    private String content;
}