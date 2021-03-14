package hcloud.platform.modules.exceltest.controller;

import hcloud.platform.base.view.ApiResponse;
import hcloud.platform.modules.plalog.model.dto.PlaLogListDto;
import hcloud.platform.utils.ExcelUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tomato
 * @date 2021/03/14/11:15
 * @desc 描述
 */
@RestController
@RequestMapping("/excel/test")
public class ExcelTestController {

    @GetMapping("/export")
    public ApiResponse export(HttpServletResponse response) {
        List<PlaLogListDto> personList = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            PlaLogListDto dto = new PlaLogListDto();
            dto.setFuncName("P001");
            dto.setLogDescription("我是描述");
            dto.setLogTime("2020-03-14 11:30:45");
            dto.setUserName("管理员");
            dto.setLogParma("{a:1,b:2,c:3,d:4}");
            dto.setLogType("修改" + i);
            personList.add(dto);
        }
        ExcelUtil.exportExcel(personList, "客户信息表", "客户表", PlaLogListDto.class, "客户表.xls", response);
        return ApiResponse.success("操作成功");
    }
}
