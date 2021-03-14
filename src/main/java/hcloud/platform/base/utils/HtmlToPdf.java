package hcloud.platform.base.utils;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;
import hcloud.platform.base.excetion.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * description 导出PDF文件转换工具
 * 创建时间 2018/9/21
 *
 * @author 杨丁辉
 */
@Component
@Slf4j
public class HtmlToPdf {

    /**
     * 生成装箱单
     *
     * @param htmlInputStream
     * @param response
     */
    public static void createPdf(InputStream htmlInputStream, HttpServletResponse response) {
        try {
            // 设置返回格式
            response.setHeader("content-Type", "application/pdf");
            response.setCharacterEncoding("UTF-8");
            // 创建pdf
            Document document = new Document(PageSize.A4, 15, 15, 30, 20);
            // 定义pdf写入
            PdfWriter mPdfWriter = PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            // 输出单张单据内容到pdf
            document.newPage();
            String pdfCss = "pdfCss";
            InputStream is = new ByteArrayInputStream(pdfCss.getBytes());
            XMLWorkerHelper.getInstance().parseXHtml(mPdfWriter, document, htmlInputStream, is, Charset.forName("UTF-8"), new ChinaFontProvide());
            // 关闭文档
            document.close();
            // 关闭pdf写入
            mPdfWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            throw new BusinessException(201, "PDF生成时出现IO异常");
        } catch (DocumentException e) {
            e.printStackTrace();
            throw new BusinessException(201, "PDF生成时出现DocumentException异常");

        }
    }

    /**
     * 提供中文
     */
    public static final class ChinaFontProvide implements FontProvider {

        @Override
        public Font getFont(final String fontname, final String encoding, final boolean embedded, final float size, final int style, final BaseColor color) {
            BaseFont bfChinese = null;
            String fontBasePath = this.getClass().getResource("/").getPath() + "font/";
            try {
                bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H",
                        BaseFont.NOT_EMBEDDED);
                //加,0 防止报错：with 'Identity-H' is not recognized.
                BaseFont.createFont(fontBasePath + "msyh.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                BaseFont.createFont(fontBasePath + "msyhl.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
                BaseFont.createFont(fontBasePath + "msyhbd.ttc,0", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Font FontChinese = new Font(bfChinese, size, style, color);
            return FontChinese;
        }

        @Override
        public boolean isRegistered(String arg0) {
            return false;
        }
    }


}
