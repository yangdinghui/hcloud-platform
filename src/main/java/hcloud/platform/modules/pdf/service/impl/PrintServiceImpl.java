package hcloud.platform.modules.pdf.service.impl;

import hcloud.platform.config.FreemarkerTemplate;
import hcloud.platform.base.utils.HtmlToPdf;
import hcloud.platform.modules.pdf.service.PrintService;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class PrintServiceImpl implements PrintService {

    private static String getPath() {
        return Thread.currentThread().getContextClassLoader().getResource("").getPath();
    }

    private void printPdf(HttpServletResponse response) throws Throwable {
        String uuid = UUID.randomUUID().toString();
        //freeMarker转换后的html的存储目录
        String convertHtmlBasePath = getPath() + "/template/";
        //html基础模板路径
        String htmlBaseTemplatePath = getPath() + "template/";
        String convertHtmlPath = convertHtmlBasePath + uuid + ".html";
        //生成html
        FreemarkerTemplate tp = new FreemarkerTemplate("UTF-8");
        tp.setTemplateDirectoryPath(htmlBaseTemplatePath);
        //封装数据 start ，必须是Map
        Map<String, Object> dataMap = new HashMap<String, Object>();
        dataMap.put("userName", "罗翔");
        dataMap.put("submitDate", "2018-09-18");
        dataMap.put("headerName", "罗老师");
        dataMap.put("agentName", "张三");
        //封装数据 end
        File convertHtmlFile = new File(convertHtmlPath);
        tp.processToFile("requestForm.html", dataMap, convertHtmlFile);
        //生成pdf方式一
//        PDFUtils.htmlFileToPDFOutStream(convertHtmlFile, response.getOutputStream());
        //生成PDF方式二
        HtmlToPdf.createPdf(new FileInputStream(convertHtmlFile), response);
    }

    @Override
    public void print(HttpServletResponse response) {
        try {
            response.setHeader("content-Type", "application/pdf");
            response.setCharacterEncoding("UTF-8");
            printPdf(response);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
