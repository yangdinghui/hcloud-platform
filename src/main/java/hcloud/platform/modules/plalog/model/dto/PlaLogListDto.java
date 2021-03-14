package hcloud.platform.modules.plalog.model.dto;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
@Data
@ApiModel(value = "PlaLogListDto", description = "日志保存返回体")
public class PlaLogListDto {
    @ApiModelProperty("业务日志编码")
    private String logCode;

    @Excel(name = "日志时间", width = 20)
    @ApiModelProperty("日志时间")
    private String logTime;
    @Excel(name = "功能名称", width = 20)
    @ApiModelProperty("功能名称")
    private String funcName;
    @ApiModelProperty("人员内码")
    private String userCode;
    @Excel(name = "日志类型", width = 20)
    @ApiModelProperty("日志类型(新增、修改、删除、审核等)")
    private String logType;
    @Excel(name = "日志说明", width = 20)
    @ApiModelProperty("日志说明")
    private String logDescription;
    @Excel(name = "人员姓名", width = 20)
    @ApiModelProperty("人员姓名")
    private String userName;
    @Excel(name = "操作记录参数", width = 40)
    @ApiModelProperty("操作记录参数")
    private String logParma;
}
