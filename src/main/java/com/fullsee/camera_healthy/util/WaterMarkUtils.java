package com.fullsee.camera_healthy.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


/**
 * 
 *  利用Java代码给图片加水印
 */
public class WaterMarkUtils {
 
	// 水印透明度
	private static float alpha = 0.5f;
 
	// 水印之间的间隔
	private static final int XMOVE = 80;
	// 水印之间的间隔
	private static final int YMOVE = 80;
 
	/**
	 * 获取文本长度。汉字为1:1，英文和数字为2:1
	 */
	private static int getTextLength(String text) {
		int length = text.length();
		for (int i = 0; i < text.length(); i++) {
			String s = String.valueOf(text.charAt(i));
			if (s.getBytes().length > 1) {
				length++;
			}
		}
		length = length % 2 == 0 ? length / 2 : length / 2 + 1;
		return length;
	}
 
	/**
	 * 
	 * @param srcImgPath
	 *            源图片路径
	 * @param tarImgPath
	 *            保存的图片路径
	 * @param waterMarkContent
	 *            水印内容
	 * @param markContentColor
	 *            水印颜色
	 * @param font
	 *            水印字体
	 */
	public void addWaterMark(String srcImgPath, String tarImgPath,
			String waterMarkContent, Color markContentColor, Font font,
			Integer degree) {
		try {
			// 读取原图片信息
			File srcImgFile = new File(srcImgPath);// 得到文件
			Image srcImg = ImageIO.read(srcImgFile);// 文件转化为图片
			int srcImgWidth = srcImg.getWidth(null);// 获取图片的宽
			int srcImgHeight = srcImg.getHeight(null);// 获取图片的高
			// 加水印
			BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight,
					BufferedImage.TYPE_INT_RGB);
			Graphics2D g = bufImg.createGraphics();
			// 设置对线段的锯齿状边缘处理
			g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BILINEAR);
			// g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
			g.drawImage(
					srcImg.getScaledInstance(srcImg.getWidth(null),
							srcImg.getHeight(null), Image.SCALE_SMOOTH), 0, 0,
					null);
			// 设置水印旋转
			if (null != degree) {
				g.rotate(Math.toRadians(degree),
						(double) bufImg.getWidth() / 2,
						(double) bufImg.getHeight() / 2);
			}
			g.setColor(markContentColor); // 根据图片的背景设置水印颜色
			g.setFont(font); // 设置字体
//			g.setFont(new Font("宋体", Font.PLAIN, 20));
//			g.setFont(new Font("宋体", Font.PLAIN, srcImg.getWidth(null)/300*15));
			// 设置水印文字透明度
//			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,
//					alpha));
 
			// 设置水印的坐标
			int x = -srcImgWidth / 2;
			int y = -srcImgHeight / 2;
			int markWidth = font.getSize() * getTextLength(waterMarkContent);// 字体长度
			int markHeight = font.getSize();// 字体高度
//			BigDecimal number= new BigDecimal(srcImg.getWidth(null));
//			BigDecimal olDnumber= new BigDecimal(800);
			
//			Integer MOVE = number.divide(olDnumber).intValue()*150;
			// 循环添加水印
			while (x < srcImgWidth * 1.5) {
				y = -srcImgHeight / 2;
				while (y < srcImgHeight * 1.5) {
					g.drawString(waterMarkContent, x, y);
					y += markHeight + YMOVE;
				}
				x += markWidth + XMOVE;
			}
 
			g.dispose();
 
			// 输出图片
			FileOutputStream outImgStream = new FileOutputStream(tarImgPath);
			String formatName = srcImgPath.substring(srcImgPath.indexOf(".") + 1, srcImgPath.length());
			ImageIO.write(bufImg, formatName, outImgStream);
			System.out.println("添加水印完成");
			outImgStream.flush();
			outImgStream.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 设置文字水印
	 * @param sourceImg 源图片路径
	 * @param targetImg 保存的图片路径
	 * @throws IOException
	 */
	public static void addWatermark(String sourceImg, String targetImg) throws IOException {
		File srcImgFile = new File(sourceImg);
		Image srcImg = ImageIO.read(srcImgFile);
		int srcImgWidth = srcImg.getWidth(null);
		int srcImgHeight = srcImg.getHeight(null);
		Font font = new Font("黑体，Arial", 1, 30);
		BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bufImg.createGraphics();
		g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
		g.setColor(Color.white);
		g.setFont(font);

		//设置水印的坐标
		g.drawString("姓名:XXX", 0, 50);
		g.drawString("床号:110", 0, 50+(50*1));
		g.drawString("日期:"+ LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")), 0, 50+(50*2));
//        g.setColor(Color.white);
//        font = new Font("黑体，Arial", 1, 120);
//        g.setFont(font);
//        g.drawString("广东省深圳(SHENZHEN)", 0, 150+(120*3));
		g.dispose();
		// 输出图片
		FileOutputStream outImgStream = new FileOutputStream(targetImg);
		ImageIO.write(bufImg, "jpg", outImgStream);
		System.out.println("添加水印完成");
		outImgStream.flush();
		outImgStream.close();
	}
 
	public int getWatermarkLength(String waterMarkContent, Graphics2D g) {
		return g.getFontMetrics(g.getFont()).charsWidth(
				waterMarkContent.toCharArray(), 0, waterMarkContent.length());
	}
 
	public static void main(String[] args) {
		Font font = new Font("宋体", Font.BOLD, 30); // 水印字体
		String srcImgPath = "E:/new/1604309034600.jpg"; // 源图片地址
		String tarImgPath = "E:/new/"+System.currentTimeMillis()+".png"; // 待存储的地址
		String waterMarkContent = LocalDateTime.now().toString(); // 水印内容
		Color color = Color.black; // 水印图片色彩以及透明度
//		Color color = new Color(107, 109, 106);
		new WaterMarkUtils().addWaterMark(srcImgPath, tarImgPath,
				waterMarkContent, color, font, -40);
	}
}