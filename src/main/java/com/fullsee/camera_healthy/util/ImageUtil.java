package com.fullsee.camera_healthy.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Arrays;

public class ImageUtil {

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 将图片转换为BASE64加密字符串.
     *
     * @param imagePath 图片路径.
     * @param format    图片格式.
     * @return
     */
    public static String convertImageToByte(String imagePath, String format) {
        File file = new File(imagePath);
        BufferedImage bi = null;
        ByteArrayOutputStream baos = null;
        String result = null;
        try {
            bi = ImageIO.read(file);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bi, format == null ? "jpg" : format, baos);
            byte[] bytes = baos.toByteArray();
            result = encoder.encodeBuffer(bytes).trim();
            System.out.println("将图片转换为BASE64加密字符串成功！");
        } catch (IOException e) {
            System.out.println("将图片转换为 BASE64加密字符串失败: " + e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                    baos = null;
                }
            } catch (Exception e) {
                System.out.println("关闭文件流发生异常: " + e);
            }
        }
        return result;
    }

    /**
     * 将图片流转换为BASE64加密字符串.
     *
     * @param imageInputStream
     * @param format           图片格式.
     * @return
     */
    public static String convertImageStreamToByte(InputStream imageInputStream, String format) {
        BufferedImage bi = null;
        ByteArrayOutputStream baos = null;
        String result = null;
        try {
            bi = ImageIO.read(imageInputStream);
            baos = new ByteArrayOutputStream();
            ImageIO.write(bi, format == null ? "jpg" : format, baos);
            byte[] bytes = baos.toByteArray();
            result = encoder.encodeBuffer(bytes).trim();
            System.out.println("将图片流转换为BASE64加密字符串成功！");
        } catch (IOException e) {
            System.out.println("将图片流转换为 BASE64加密字符串失败: " + e);
        } finally {
            try {
                if (baos != null) {
                    baos.close();
                    baos = null;
                }
            } catch (Exception e) {
                System.out.println("关闭文件流发生异常: " + e);
            }
        }
        return result;
    }

    /**
     * 将base64格式的字符串转换成二进制流，并转换成图片
     */
    public static byte[] changeBase64ToBytes(String base64String) {
        // base64格式字符串为空，返回false
        if (base64String == null) {
            return null;
        }
        BASE64Decoder decoder = new BASE64Decoder();
        try {
            //解码过程，即将base64字符串转换成二进制流
            return decoder.decodeBuffer(base64String);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将BASE64加密字符串转换为图片.
     *
     * @param base64String
     * @param imagePath    图片生成路径.
     * @param format       图片格式.
     */
    public static void convertByteToImage(String base64String, String imagePath, String format) {
        byte[] bytes = null;
        ByteArrayInputStream bais = null;
        BufferedImage bi = null;
        File file = null;
        try {
            bytes = decoder.decodeBuffer(base64String);
            bais = new ByteArrayInputStream(bytes);
            bi = ImageIO.read(bais);
            file = new File(imagePath);
            File fileParent = file.getParentFile();
            // 判断文件是否存在，不存在就创建一个新的
            if (!fileParent.exists()) {
                // 使文件可以改，因为Tomcat发布服务后，文件的权限不一定是可以改的
                boolean b = fileParent.setWritable(true);
                // 使用dirs是为了解决上传的路径中，如果有文件夹的没有创建，其会自动创建文件夹
                boolean mkdirs = fileParent.mkdirs();
            }
            boolean newFile = file.createNewFile();
            ImageIO.write(bi, format == null ? "jpg" : format, file);
            System.out.println("将BASE64加密字符串转换为图片成功！");
        } catch (IOException e) {
            System.out.println("将BASE64加密字符串转换为图片失败: " + e);
        } finally {
            try {
                if (bais != null) {
                    bais.close();
                    bais = null;
                }
            } catch (Exception e) {
                System.out.println("关闭文件流发生异常: " + e);
            }
        }
    }

    /**
     * 将BASE64加密字符串转换为输入流
     *
     * @param base64String
     */
    public static ByteArrayInputStream convertByteToInputStream(String base64String) {
        //base64字符串
        String file = "XXXXXXXXXX";
        //将字符串转换为byte数组
        byte[] bytes = new byte[0];
        try {
            bytes = new BASE64Decoder().decodeBuffer(file.trim());
            //转化为输入流
            return new ByteArrayInputStream(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 在线图片转换成base64字符串
     *
     * @param imgURL 图片线上路径
     * @return
     * @author ZHANGJL
     * @dateTime 2018-02-23 14:43:18
     */
    public static String ImageToBase64ByOnline(String imgURL) {
        ByteArrayOutputStream data = new ByteArrayOutputStream();
        try {
            // 创建URL
            URL url = new URL(imgURL);
            byte[] by = new byte[1024];
            // 创建链接
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            InputStream is = conn.getInputStream();
            // 将内容读取内存中
            int len = -1;
            while ((len = is.read(by)) != -1) {
                data.write(by, 0, len);
            }
            // 关闭流
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(data.toByteArray()).replaceAll("\r|\n", "");
    }

    /**
     * 将图片写入到磁盘
     *
     * @param img      图片数据流
     * @param filePath 文件储存地址
     * @param fileName 文件保存时的名称
     */
    public static void writeImageToDisk(byte[] img, String filePath, String fileName) {
        try {
            File file = new File(filePath);
            // 判断文件是否存在，不存在就创建一个新的
            if (!file.exists()) {
                // 使文件可以改，因为Tomcat发布服务后，文件的权限不一定是可以改的
                boolean b = file.setWritable(true);
                // 使用dirs是为了解决上传的路径中，如果有文件夹的没有创建，其会自动创建文件夹
                boolean mkdirs = file.mkdirs();
            }
            FileOutputStream fops = new FileOutputStream(file + "\\" + fileName);
            fops.write(img);
            fops.flush();
            fops.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据地址获得数据的字节流
     *
     * @param strUrl 网络连接地址
     * @return
     */
    public static byte[] getImageFromNetByUrl(String strUrl) {
        try {
            URL url = new URL(strUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5 * 1000);
            InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
            return readInputStream(inStream);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将二进制图片流转 base64
     *
     * @param bytes 二进制图片流
     * @return
     */
    public static String convertByte2Base64(byte[] bytes) {
        String encode = encoder.encode(bytes);
        String suffix = getSuffix(bytes);
        return "data:image/" + suffix.substring(suffix.indexOf(".") + 1) + ";base64," + encode;
    }

    /**
     * 从输入流中获取数据
     *
     * @param inStream 输入流
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 获取文件后缀名
     *
     * @param source
     * @return
     */
    public static String getSuffix(byte[] source) {
        byte[] byteSuffix = Arrays.copyOf(source, 3);
        String hexSuffix = bytesToHexString(byteSuffix);
        if (null == hexSuffix) return ".jpg";
        switch (hexSuffix) {
            case "89504e":
                return ".png";
            case "ffd8ff":
                return ".jpg";
            default:
                return ".jpg";
        }
    }

    public static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (byte b : src) {
            int v = b & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString().toLowerCase();
    }

}
