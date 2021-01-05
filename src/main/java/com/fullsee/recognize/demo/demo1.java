package com.fullsee.recognize.demo;

import java.util.ArrayList;
import java.util.List;
 
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
 
/**
 * @Description 背景去除 简单案列，只适合背景单一的图像
 * @author XPY
 * @date 2016年8月30日下午4:14:32
 */
public class demo1 {
	static {
		//在使用OpenCV前必须加载Core.NATIVE_LIBRARY_NAME类,否则会报错
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
	}
	public static void main(String[] args) {
		Mat img = Imgcodecs.imread("E:\\new\\find11.jpg");//读图像
		Mat new_img = doBackgroundRemoval(img);
//		Imgcodecs.imwrite("C:\\Users\\fullsee\\Desktop\\lion1.jpg",new_img);//写图像
		HighGui.imshow("111", new_img);
		HighGui.waitKey(0);
	}
 
	private static Mat doBackgroundRemoval(Mat frame) {
		// init
		Mat hsvImg = new Mat();
		List<Mat> hsvPlanes = new ArrayList<>();
		Mat thresholdImg = new Mat();
 
		int thresh_type = Imgproc.THRESH_BINARY_INV;
 
		// threshold the image with the average hue value
		hsvImg.create(frame.size(), CvType.CV_8U);
		Imgproc.cvtColor(frame, hsvImg, Imgproc.COLOR_BGR2HSV);
		Core.split(hsvImg, hsvPlanes);
 
		// get the average hue value of the image
 
		Scalar average = Core.mean(hsvPlanes.get(0));
		double threshValue = average.val[0];
		Imgproc.threshold(hsvPlanes.get(0), thresholdImg, threshValue, 179.0,
				thresh_type);
 
		Imgproc.blur(thresholdImg, thresholdImg, new Size(5, 5));
 
		// dilate to fill gaps, erode to smooth edges
		Imgproc.dilate(thresholdImg, thresholdImg, new Mat(),
				new Point(-1, -1), 1);
		Imgproc.erode(thresholdImg, thresholdImg, new Mat(), new Point(-1, -1),
				3);
 
		Imgproc.threshold(thresholdImg, thresholdImg, threshValue, 179.0,
				Imgproc.THRESH_BINARY);
 
		// create the new image
		Mat foreground = new Mat(frame.size(), CvType.CV_8UC3, new Scalar(255,
				255, 255));
		thresholdImg.convertTo(thresholdImg, CvType.CV_8U);
		frame.copyTo(foreground, thresholdImg);// 掩膜图像复制
		return foreground;
	}
}