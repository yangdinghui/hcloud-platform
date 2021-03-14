package hcloud.platform.modules.server.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class Message {
    @NotBlank(message = "标题不能为空")
    @ApiModelProperty("消息标题，最长为256，必填")
    private String text;
    @ApiModelProperty("消息内容，最长64Kb，可空，支持MarkDown")
    private String desp;
}
