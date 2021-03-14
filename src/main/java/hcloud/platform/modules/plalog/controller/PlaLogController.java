package hcloud.platform.modules.plalog.controller;

import com.github.pagehelper.PageHelper;
import hcloud.platform.aop.ActionLog;
import hcloud.platform.base.view.ApiResponse;
import hcloud.platform.base.view.ListModel;
import hcloud.platform.base.utils.RedisUtils;
import hcloud.platform.modules.plalog.model.dto.PlaLogListDto;
import hcloud.platform.modules.plalog.model.vo.PlaLogSaveVo;
import hcloud.platform.modules.plalog.service.PlaLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * description 描述这个类的主要功能、用途
 * 创建时间 2019/9/1
 *
 * @author 杨丁辉
 */
@RestController
@RequestMapping(value = "/plaLog", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@Api(value = "日志相关接口", tags = "日志相关")
public class PlaLogController {
    private PlaLogService plaLogService;
    private RedisUtils redisUtils;

    public PlaLogController(PlaLogService plaLogService, RedisUtils redisUtils) {
        this.plaLogService = plaLogService;
        this.redisUtils = redisUtils;
    }

    @ApiOperation(value = "日志列表查询接口", notes = "日志列表查询")
    @GetMapping("/list")
    public ApiResponse<ListModel<PlaLogListDto>> list() {
        PageHelper.startPage(0,10);
        return ApiResponse.success(plaLogService.list());
    }

    @ApiOperation(value = "日志列表保存接口", notes = "日志列表保存")
    @ActionLog
    @PostMapping(value = "/save")
    public ApiResponse<String> save(@RequestBody @Valid PlaLogSaveVo vo) {
        boolean result = plaLogService.save(vo);
        if (result) {
            return ApiResponse.success("保存成功");
        }
        return ApiResponse.fail("保存失败");
    }

    @ApiOperation(value = "redis获取值测试接口", notes = "redis获取值测试接口")
    @PostMapping(value = "/redisTest/get")
    public ApiResponse redisTest(String key) {
//        String test1 = (String) redisUtils.get(key);
        return ApiResponse.success("取值成功", redisUtils.get(key));
    }

    @ApiOperation(value = "redis设值测试接口", notes = "redis设值测试接口")
    @PostMapping(value = "/redisTest/set")
    public ApiResponse<String> redisTestSet(String key, String value) {
//        redisTemplate.opsForValue().set(key, value);
        redisUtils.set(key, value);
        return ApiResponse.success("保存成功");
    }
}
