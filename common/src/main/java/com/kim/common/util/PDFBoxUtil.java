package com.kim.common.util;

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.encryption.*;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author kim
 * @Since 2021/4/22
 * PDFBox操作pdf工具类
 */
public class PDFBoxUtil {


    /**
     * 获取pdf文本内容
     * */
    public static String getPdfText(InputStream pdf){
        //加载pdf文件进内存对象：PDFDocument
        try (PDDocument document = PDDocument.load(pdf)) {
            int startPage = 1;
            //总页数
            int endPage = document.getNumberOfPages();
            //实例化pdf文本解析器
            PDFTextStripper textStripper = new PDFTextStripper();
            //是否排序：true：排序，false：不排序
            textStripper.setSortByPosition(true);
            //设置首尾页
            textStripper.setStartPage(startPage);
            textStripper.setEndPage(endPage);
            //获取文本信息
            String text = textStripper.getText(document);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 提取pdf所有图片
     * */
    public static List<ByteArrayOutputStream> getImages(InputStream pdf){
        List<ByteArrayOutputStream> imgOutputs = new ArrayList<>();
        //加载pdf文件进内存对象：PDFDocument
        try (PDDocument document = PDDocument.load(pdf)) {
            List<PDImageXObject> images = new ArrayList();
            //获取全部页面对象
            PDPageTree pages = document.getPages();
            //遍历全部页面
            for (int i = 0; i < pages.getCount(); i++) {
                //获取每个页面
                PDPage page = pages.get(i);
                //获取该页面的所有非文本对象名称集合
                Iterable<COSName> objNames = page.getResources().getXObjectNames();
                //遍历非文本对象集合
                for (COSName cosName : objNames
                ) {
                    //提取图片对象装进集合
                    if (page.getResources().isImageXObject(cosName)) {
                        images.add((PDImageXObject) page.getResources().getXObject(cosName));
                    }
                }
            }
            //将PDImageXObject对象转为输出流对象，并且写到磁盘
            for (PDImageXObject pdImage : images
            ) {
                BufferedImage bufferedImage = pdImage.getImage();
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                ImageIO.write(bufferedImage, "bmp", outputStream);
                imgOutputs.add(outputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return imgOutputs;

    }


    /**
     * 对pdf文档加密
     * */
    public static void  encryptPDF(InputStream pdf,String ownerPassword,String userPassword,OutputStream out)  {
        //加载pdf文件进内存对象：PDFDocument
        try (PDDocument document = PDDocument.load(pdf)) {
            //实例化加密权限器
            AccessPermission accessPermission = new AccessPermission();
            //不能提取内容
            accessPermission.setCanExtractContent(false);
            //不能打印
            accessPermission.setCanPrint(false);
            //不能修改
            accessPermission.setCanModify(false);
            //将加密信息集成到pdf文档
            StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy(ownerPassword, userPassword, accessPermission);
            SecurityHandler securityHandler = new StandardSecurityHandler(standardProtectionPolicy);
            securityHandler.prepareDocumentForEncryption(document);
            PDEncryption pdEncryption = new PDEncryption();
            pdEncryption.setSecurityHandler(securityHandler);
            document.setEncryptionDictionary(pdEncryption);
            //加密后的pdf文档输出文件
            document.save(out);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * pdf解密
     * */
    public void decryptPDF(InputStream pdf,String ownerPassword,OutputStream out) throws IOException {
        PDDocument document=PDDocument.load(pdf,ownerPassword);
        document.setAllSecurityToBeRemoved(true);
        document.save(out);
    }


    public static class PDFTextObj extends PDFTextStripper{

        /**
         * Instantiate a new PDFTextStripper object.
         *
         * @throws IOException If there is an error loading the properties.
         */
        public PDFTextObj() throws IOException {

        }

        @Override
        protected List<List<TextPosition>> getCharactersByArticle() {
            return super.getCharactersByArticle();
        }
    }

    /**
     * 获取pdf文本位置信息，包含下标，xy轴下标等。
     * */
    public static List<List<TextPosition>> PDFObj(InputStream pdf) throws Exception{
        //加载pdf文件进内存对象：PDFDocument
        PDFTextObj textStripper;
        try (PDDocument document = PDDocument.load(pdf)) {
            int startPage = 1;
            //总页数
            int endPage = document.getNumberOfPages();
            //实例化pdf文本解析器
            textStripper = new PDFTextObj();
            //是否排序：true：排序，false：不排序
            textStripper.setSortByPosition(true);

            //设置首尾页
            textStripper.setStartPage(startPage);
            textStripper.setEndPage(endPage);
            //获取文本信息
            String text = textStripper.getText(document);
        }
        //获取文本对象集合，包含坐标，下标，文字信息
        List<List<TextPosition>> textPositions=textStripper.getCharactersByArticle();
        return textPositions;
    }


    /**
     * pdf转成图片
     * */
    public static List<OutputStream> convertPdfToImage(InputStream pdf) throws IOException {
        List<OutputStream> outs=new ArrayList<>();
        try (PDDocument document = PDDocument.load(pdf)) {
            PDFRenderer renderer = new PDFRenderer(document);
            for (int i = 0; i < document.getNumberOfPages(); i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144);
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                ImageIO.write(image, "jpg", out);
               outs.add(out);
            }
            return outs;
        }
    }






}
