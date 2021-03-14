package hcloud.platform.modules.server.controller;

import com.alibaba.fastjson.JSONObject;
import hcloud.platform.aop.ActionLog;
import hcloud.platform.base.view.ApiResponse;
import hcloud.platform.modules.server.entity.Message;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/server")
@Api(value = "server酱测试接口", tags = "server酱测试接口")
public class TestServerController {
    @Autowired
    private TestServer testServer;

    @ApiOperation(value = "server酱GET方式推送接口", notes = "server酱GET方式推送接口")
    @ActionLog
    @GetMapping(value = "/get/push/{message}")
    public ApiResponse<JSONObject> push(@PathVariable String message) {
        return testServer.push(message);
    }

    @ApiOperation(value = "server酱POST方式推送接口", notes = "server酱POST方式推送接口")
    @ActionLog
    @PostMapping(value = "/post/push")
    public ApiResponse<JSONObject> pushPost(@RequestBody @Valid Message message) {
        return testServer.pushPost(message);
    }
}
