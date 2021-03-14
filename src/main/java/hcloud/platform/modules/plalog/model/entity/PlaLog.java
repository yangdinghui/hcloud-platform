package hcloud.platform.modules.plalog.model.entity;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "pla_log")
public class PlaLog {
    /**
     * 业务日志编码
     */
    @Id
    @Column(name = "logCode")
    private String logCode;

    /**
     * 日志时间
     */
    @Column(name = "logTime")
    private Date logTime;

    /**
     * 功能名称
     */
    @Column(name = "funcName")
    private String funcName;

    /**
     * 人员内码
     */
    @Column(name = "userCode")
    private String userCode;

    /**
     * 日志类型(新增、修改、删除、审核等)
     */
    @Column(name = "logType")
    private String logType;

    /**
     * 日志说明
     */
    @Column(name = "logDescription")
    private String logDescription;

    /**
     * 人员姓名
     */
    @Column(name = "userName")
    private String userName;

    /**
     * 操作记录参数
     */
    @Column(name = "logParma")
    private String logParma;
}