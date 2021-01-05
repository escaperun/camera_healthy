package com.fullsee.camera_healthy.adapter;

import com.fullsee.camera_healthy.fastdfs.FastDfsService;
import com.fullsee.camera_healthy.fastdfs.impl.FastFastDfsServiceImpl;
import com.fullsee.camera_healthy.util.WaterMarkUtils;
import com.fullsee.camera_healthy.websocket.WebSocketServer;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.Java2DFrameConverter;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author fullsee
 */
public class SavePhotoMouseAdapter implements ActionListener {
 
 
	private opencv_core.IplImage iplImage;


	private FastFastDfsServiceImpl fastDfsService = new FastFastDfsServiceImpl();
	
	public SavePhotoMouseAdapter(opencv_core.IplImage iplImage) {
		this.iplImage = iplImage;
	}
 
 
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("保存");
		// 保存结果提示框
		JFrame myFrame = new JFrame();
		try {
			if (iplImage != null) {
				String targetFileName = "E:/new/"+System.currentTimeMillis()+".jpg";
				// 保存图片
				cvSaveImage(targetFileName, iplImage);
				// 发送修改用户头像请求...也可以直接发送字节数组到服务器，由服务器上传图片并修改用户头像
//				JOptionPane.showMessageDialog(myFrame, "上传成功");
				// 添加水印
//				WaterMarkUtils.addWatermark(targetFileName,targetFileName);
				String base64 = getBase64(targetFileName);
//				WebSocketServer.sendInfo("data:image/png;base64,"+getBase64(targetFileName), null);
				String imageUrl = fastDfsService.uploadImageBYBase64( base64, System.currentTimeMillis() + ".jpg");
//				System.out.println(imageUrl);
			}
		} catch (IOException ioException) {
			JOptionPane.showMessageDialog(myFrame, "保存失败");
			ioException.printStackTrace();
		} finally {
			// 关闭提示jframe
			myFrame.dispose();
			myFrame = null;
		}
	}
 
 
	public static void cvSaveImage(String path, opencv_core.IplImage image) throws IOException {
		File file = new File(path);
		// ImageIO.write(toBufferedImage(image), "jpg", file);
		// 使用字节保存
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ImageIO.write(toBufferedImage(image), "jpg", out);
		byte[] bs = out.toByteArray();
 
		// 保存字节数组为图片到本地
		FileOutputStream fos = new FileOutputStream(file);
		fos.write(bs, 0, bs.length);
		fos.close();
 
		out.close();
	}
 
 
	// 通过image获取bufferedImage
	//Java2DFrameConverter让Frame和BufferedImage之间相互转换
	public static BufferedImage toBufferedImage(opencv_core.IplImage image) {
		OpenCVFrameConverter.ToIplImage iplConverter = new OpenCVFrameConverter.ToIplImage();
		Java2DFrameConverter java2dConverter = new Java2DFrameConverter();
		BufferedImage bufferedImage = java2dConverter.convert(iplConverter.convert(image));
		return bufferedImage;
	}
	// 图片转换base64
	public static String getBase64(String path){
		File file = new File(path);
		String base64 = null;
		try {
			BufferedImage image = ImageIO.read(file);
			Integer width = image.getWidth();
			Integer height = image.getHeight();
			System.out.println("宽：" + width + " 高:"+height);

			//输出流
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			ImageIO.write(image, "png", stream);
			base64 = Base64.encode(stream.toByteArray());
//			System.out.println(base64);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return base64;
	}

}
