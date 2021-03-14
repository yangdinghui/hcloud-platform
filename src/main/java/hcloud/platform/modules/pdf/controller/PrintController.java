package hcloud.platform.modules.pdf.controller;

import hcloud.platform.modules.pdf.service.PrintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * @author Administrator
 */
@RestController
@RequestMapping(value = "")
@Api(value = "生成pdf文件接口", tags = "生成pdf文件接口")
public class PrintController {
    private PrintService printService;

    public PrintController(PrintService printService) {
        this.printService = printService;
    }

    @ApiOperation(value = "生成pdf接口", notes = "生成pdf接口")
    @PostMapping("/print")
    public void print(HttpServletResponse response) {
        printService.print(response);
    }
}
