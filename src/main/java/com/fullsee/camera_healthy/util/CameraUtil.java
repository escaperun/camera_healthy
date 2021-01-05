package com.fullsee.camera_healthy.util;

import com.fullsee.camera_healthy.adapter.SavePhotoMouseAdapter;
import com.fullsee.camera_healthy.adapter.TakePhotoMouseAdapter;
import com.fullsee.camera_healthy.entity.Camera;
import org.bytedeco.javacpp.opencv_core;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * @Description:
 * @author: fullsee
 * @date: 2020/11/3 17:15
 */
public class CameraUtil {

    public static JButton take_photo = null;
    public static JButton save_photo = null;

    public static void CameraInit() throws Exception, IOException, InterruptedException {
        OpenCVFrameGrabber grabber = new OpenCVFrameGrabber(1);
        grabber.setImageWidth(800);
        grabber.setImageHeight(640);
        grabber.start();


        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        opencv_core.IplImage grabbedImage = converter.convertToIplImage(grabber.grab());
        CanvasFrame frame = new CanvasFrame("Some Title", CanvasFrame.getDefaultGamma() / grabber.getGamma());
        // frame.setSize(800, 600);
        //frame.setBounds(200, 100, 640, 640);
        // 设置操作界面
        JPanel contentPane = new JPanel();
        //contentPane.setBounds(0, 0, 640, 640);
        Container contentPane2 = frame.getContentPane();


        take_photo = new JButton("拍照");
        save_photo = new JButton("保存");
        JButton cancle = new JButton("关闭");
        Camera camera = new Camera();
        // 监听拍摄
        take_photo.addActionListener(new TakePhotoMouseAdapter(take_photo, camera));
        // 监听保存
        save_photo.addActionListener(new SavePhotoMouseAdapter(grabbedImage));
        // 关闭
        cancle.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
            }

//            public void mouseClicked(MouseEvent arg0) {
//                frame.setVisible(false);
//            }
        });
        // 添加按钮
        contentPane.add(take_photo, BorderLayout.SOUTH);
        contentPane.add(save_photo, BorderLayout.SOUTH);
        contentPane.add(cancle, BorderLayout.SOUTH);
        // 添加面板
        contentPane2.add(contentPane, BorderLayout.SOUTH);
        // 操作状态
        while (frame.isVisible()) {


            // 获取图像
            if (camera.getState()) {
                grabbedImage = converter.convert(grabber.grab());
            }
            frame.showImage(converter.convert(grabbedImage));
            // 每40毫秒刷新视频,一秒25帧
            Thread.sleep(40);
        }


        frame.dispose();


        grabber.stop();
    }
}
