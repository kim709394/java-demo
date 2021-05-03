package com.kim.common.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @author huangjie
 * @description itext处理PDF
 * @date 2020/5/15
 */
public class ItextPDFUtils {


    private static final String PAGE_NUM_FORMAT = "第%d页，共%d页";

    /**
     * 合并PDF为一个新的PDF(带页码，默认样式)
     * 有序，新的PDF页按输入的PDF文件流集合顺序，以及每个被合成的PDF文件页的顺序合成
     *
     * @param srcPDFs   待合成的PDF
     * @param targetPDF 输出合成后的新的PDF
     */
    public static void mergePDFWithPageNum(LinkedList<InputStream> srcPDFs, OutputStream targetPDF) {
        mergePDF(srcPDFs, targetPDF, PAGE_NUM_FORMAT);
    }

    /**
     * 合并PDF为一个新的PDF(带页码，默认居中)
     * 有序，新的PDF页按输入的PDF文件流集合顺序，以及每个被合成的PDF文件页的顺序合成
     *
     * @param srcPDFs       待合成的PDF
     * @param targetPDF     输出合成后的新的PDF
     * @param pageNumFormat 页码样式
     */
    public static void mergePDF(LinkedList<InputStream> srcPDFs, OutputStream targetPDF, String pageNumFormat) {

        mergePDFImpl(srcPDFs, targetPDF, pageNumFormat);

    }

    /**
     * 合并PDF为一个新的PDF(无页码)
     * 有序，PDF页按输入的PDF文件流集合顺序，以及每个被合成的PDF文件页的顺序合成
     *
     * @param srcPDFs   待合成的PDF
     * @param targetPDF 输出合成后的新的PDF
     */
    public static void mergePDF(LinkedList<InputStream> srcPDFs, OutputStream targetPDF) {

        mergePDF(srcPDFs, targetPDF, null);

    }


    /**
     * 给PDF增加水印(默认样式)
     *
     * @param src 原PDF
     * @param target 新的PDF
     * @param waterMarkText 水印文本
     */
    public static void addWaterMark(InputStream src, OutputStream target, String waterMarkText) throws Exception {

        PdfStamper stamper = null;
        PdfReader reader = null;
        try {
            reader = new PdfReader(src);

            stamper = new PdfStamper(reader, target);

            int total = reader.getNumberOfPages() + 1;
            PdfContentByte content;

            PdfGState gs = new PdfGState();
            gs.setFillOpacity(0.2f);

            BaseFont base = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.EMBEDDED);

            for (int i = 1; i < total; i++) {

                content = stamper.getOverContent(i);
                content.setGState(gs);
                content.setFontAndSize(base, 64);

                content.beginText();
                // 设置颜色 默认为黑色
                content.setColorFill(BaseColor.BLACK);
                // 开始写入水印

                content.showTextAligned(Element.ALIGN_MIDDLE, waterMarkText, 180, 340, 45);
                content.endText();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            stamper.close();
            reader.close();
        }

    }


    private static void mergePDFImpl(LinkedList<InputStream> srcPDFs, OutputStream targetPDF, String pageNumFormat) {
        Document document = null;
        PdfCopy copy = null;
        try {
            document = new Document();
            copy = new PdfCopy(document, targetPDF);

            document.open();
            int totalPagesNum = 0;//总页码
            int currentPageNum = 0;//当前页码
            List<PdfReader> readerList = new LinkedList<>();
            for (int i = 0; i < srcPDFs.size(); i++) {
                PdfReader reader = new PdfReader(srcPDFs.get(i));
                totalPagesNum += reader.getNumberOfPages();
                readerList.add(reader);
            }
            for (int i = 0; i < readerList.size(); i++) {
                PdfReader reader = readerList.get(i);
                int n = reader.getNumberOfPages();
                for (int j = 1; j <= n; j++) {
                    currentPageNum++;
                    document.newPage();
                    PdfImportedPage page = copy.getImportedPage(reader, j);
                    if (pageNumFormat != null && !pageNumFormat.equals("")) {
                        String format = String.format(pageNumFormat, currentPageNum, totalPagesNum);
                        addPageNum(copy, page, document, format);
                    }
                    copy.addPage(page);
                }
                reader.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        } finally {
            copy.close();
            document.close();
        }
    }

    private static void addPageNum(PdfCopy copy, PdfImportedPage pdfImportedPage, Document document, String pageNumMsg) throws IOException {
        PdfCopy.PageStamp pageStamp = copy.createPageStamp(pdfImportedPage);
        float x = (document.left() + document.right()) / 2;
        float y = document.bottom(-10);
        ColumnText.showTextAligned(pageStamp.getUnderContent(), Element.ALIGN_CENTER, new Phrase(addFont(pageNumMsg)), x, y, 0f);
        pageStamp.alterContents();
    }


    private static Paragraph addFont(String content) {
        BaseFont baseFont = null;
        try {
            try {
                baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            } catch (DocumentException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Font font = null;
        font = new Font(baseFont, 12f, Font.NORMAL);
        return addText(content, font);
    }

    private static Paragraph addText(String content, Font font) {
        Paragraph paragraph = new Paragraph(content, font);
        paragraph.setAlignment(Element.ALIGN_LEFT);
        return paragraph;
    }


}
