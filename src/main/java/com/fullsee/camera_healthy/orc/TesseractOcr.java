/*
package com.fullsee.camera_healthy.orc;

import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;
import net.sourceforge.tess4j.util.LoadLibs;

import java.io.File;

*/
/**
 * @Description: todo
 * @author: fullsee
 * @date: 2020/11/30 10:55
 *//*

public class TesseractOcr {

    public static void main(String[] args) throws TesseractException {
        //加载待读取图片
        File imageFile = new File("E:\\new\\find14.jpg");
        //创建tess对象
        ITesseract instance = new Tesseract();
        //这样就能使用classpath目录下的训练库了
//        File tessDataFolder = LoadLibs.extractTessResources("tessdata");
        //设置训练文件目录
        instance.setDatapath("D:\\Soft\\Tesseract-OCR\\tessdata");
//        instance.setDatapath(tessDataFolder.getAbsolutePath());
        //设置训练语言
        instance.setLanguage("eng");
        //执行转换
        String result = instance.doOCR(imageFile);
        System.out.println(result);
    }
}
*/
