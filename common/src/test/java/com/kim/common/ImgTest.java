package com.kim.common;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import net.coobird.thumbnailator.resizers.BicubicResizer;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.util.FileCopyUtils;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @Author kim
 * @Since 2021/4/21
 * 图片处理
 */
public class ImgTest {

    /**
     * 获取图片真实类型
     * */
    @Test
    public void testImageActuallyType() throws Exception {

        ImageInputStream imageInputStream = ImageIO.createImageInputStream(this.getClass().getClassLoader().getResourceAsStream("img/test1.jpg"));
        Iterator<ImageReader> iterator = ImageIO.getImageReaders(imageInputStream);
        if (iterator.hasNext()) {
            ImageReader reader = iterator.next();
            System.out.println(reader.getFormatName());
            imageInputStream.close();
        }
    }

    /**
     * 拼接图片url:
     * data:base64,img;+base64String(只适合小文件)
     */
    @Test
    public void testDataUrlScheme() throws IOException {

        InputStream in = this.getClass().getClassLoader().getResourceAsStream("img/test1.jpg");
        String base64 = Base64.getEncoder().encodeToString(IOUtils.toByteArray(in));
        System.out.println(base64);
    }

    /**
     * thumbnailator工具图片压缩
     * */
    @Test
    public void imageCompress() throws IOException {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("img/test3.jpg");
        File out=new File("D:/test3.jpg");
        Thumbnails.of(inputStream)
                .size(608,608)
                .outputFormat("jpg")
                .toFile(out);
    }

    /***
     * thumbnailator工具图片压缩2
     * */
    @Test
    public void imageCompress2() throws IOException {

        InputStream in=new FileInputStream("D:\\test\\img_0.jpg");
        byte[] b= FileCopyUtils.copyToByteArray(in);

        BufferedImage read = ImageIO.read(new ByteArrayInputStream(b));
        Map<String,Integer> map=resize(read.getWidth(),read.getHeight());
        File out=new File("d:/test3.jpg");
        Thumbnails.of(new ByteArrayInputStream(b))
                //.scalingMode(ScalingMode.BICUBIC)
                .forceSize(map.get("width"),map.get("height"))
                .resizer(new BicubicResizer())
                .outputFormat("jpg")
                .toFile(out);

    }

    private Map<String, Integer> resize(int width, int height) {
        int scale = 608;
        int maxScale = 2048;
        Double zoom = Double.valueOf(scale + "") / (double)Integer.min(width, height);
        if (zoom * (double)Integer.max(width, height) > (double)maxScale) {
            zoom = Double.valueOf(maxScale + "") / (double)Integer.max(width, height);
        }

        int newWidth = (int)((double)width * zoom);
        int newHeight = (int)((double)height * zoom);
        newWidth -= newWidth % 32;
        newHeight -= newHeight % 32;
        Map<String, Integer> map = new HashMap();
        map.put("width", newWidth);
        map.put("height", newHeight);
        return map;
    }


    /**
     * thumbnailator工具图片分割
     * */
    @Test
    public void imageSplit() throws IOException {
        BufferedImage read = ImageIO.read(this.getClass().getClassLoader().getResourceAsStream("img/test4.png"));
        File out=new File("D:/test3.jpg");
        int width=read.getWidth();
        int height=read.getHeight();
        Thumbnails.of(read)
                .sourceRegion(Positions.TOP_RIGHT,width/2,height)
                .resizer(new BicubicResizer())
                .forceSize(width/2,height)
                //.keepAspectRatio(false)
                //.outputFormat("jpg")
                .toFile(out);

    }




}
