package hcloud.platform.modules.sendmail.model;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "mail_scheduling")
public class MailScheduling {
    /**
     * 调度编码
     */
    @Id
    @Column(name = "scheduleCode")
    private String scheduleCode;

    /**
     * 接收人姓名
     */
    @Column(name = "recipientName")
    private String recipientName;

    /**
     * 创建时间
     */
    @Column(name = "createDate")
    private Date createDate;

    /**
     * 邮件标题
     */
    @Column(name = "mailTitle")
    private String mailTitle;

    /**
     * 发送方式:A-自动,B-自定义
     */
    @Column(name = "type")
    private String type;

    /**
     * 发送内容
     */
    @Column(name = "content")
    private String content;
}