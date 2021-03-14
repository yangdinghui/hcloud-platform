package hcloud.platform.modules.plalog.model.vo;

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
@ApiModel(value = "PlaLogSaveVo",description = "日志保存请求体")
public class PlaLogSaveVo{

   @ApiModelProperty("业务日志编码")
    private String logCode;

    @ApiModelProperty("日志时间")
    private String logTime;

    @ApiModelProperty("功能名称")
    private String funcName;

    @ApiModelProperty("人员内码")
    private String userCode;

    @ApiModelProperty("日志类型(新增、修改、删除、审核等)")
    private String logType;

    @ApiModelProperty("日志说明")
    private String logDescription;

    @ApiModelProperty("人员姓名")
    private String userName;

    @ApiModelProperty("操作记录参数")
    private String logParma;
}
