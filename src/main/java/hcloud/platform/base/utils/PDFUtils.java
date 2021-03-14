package hcloud.platform.base.utils;

import com.itextpdf.text.pdf.BaseFont;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PDFUtils {
    private static String fontBasePath = PDFUtils.class.getResource("/").getPath() + "font/";

    public static void htmlFileToPDFStream(File htmlFile, OutputStream pdfOutStream,
                                           File imageBaseURL) throws Throwable {
        if (htmlFile == null) {
            throw new RuntimeException("htmlFile is null");
        }
        if (pdfOutStream == null) {
            throw new RuntimeException("output is null");
        }

        // 生成ITextRenderer实例
        ITextRenderer renderer = new ITextRenderer();
        // 关联html页面
        renderer.setDocument(htmlFile.toURI().toURL().toString());
        if (imageBaseURL != null) {
            // 设置获取图片的基本路径
            renderer.getSharedContext().setBaseURL(imageBaseURL.toURI().toURL().toString());
        }
        // 设置字体路径，必须和html设置一致
        ITextFontResolver fontResolver = renderer.getFontResolver();
        fontResolver.addFont(fontBasePath + "msyh.ttc", BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(fontBasePath + "msyhl.ttc", BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);
        fontResolver.addFont(fontBasePath + "msyhbd.ttc", BaseFont.IDENTITY_H,
                BaseFont.NOT_EMBEDDED);
        renderer.layout();
        renderer.createPDF(pdfOutStream);
    }

    /**
     * 将html文件流渲染为pdf文件并生成本地pdf文件
     *
     * @param htmlFile
     * @param pdfFile
     * @param imageBaseURL
     * @throws Throwable
     */
    public static void htmlFileToPDFFile(File htmlFile, File pdfFile,
                                         File imageBaseURL) throws Throwable {
        OutputStream pdfOutStream = new FileOutputStream(pdfFile);
        try {
            htmlFileToPDFStream(htmlFile, pdfOutStream, imageBaseURL);
        } finally {
            if (htmlFile != null) {
                htmlFile.delete();
            }
            if (pdfOutStream != null) {
                pdfOutStream.close();
            }
        }
    }

    /**
     * 将html文件流渲染为pdf文件并写入outStream
     *
     * @param htmlFile
     * @param pdfOutputStream
     * @throws Throwable
     */
    public static void htmlFileToPDFOutStream(File htmlFile, OutputStream pdfOutputStream) throws Throwable {
        try {
            htmlFileToPDFStream(htmlFile, pdfOutputStream, null);
        } finally {
            if (htmlFile != null) {
                htmlFile.delete();
            }
        }
    }

}
