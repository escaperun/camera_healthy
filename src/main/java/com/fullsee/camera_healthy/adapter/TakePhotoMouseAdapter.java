package com.fullsee.camera_healthy.adapter;

import com.fullsee.camera_healthy.entity.Camera;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author fullsee
 */
public class TakePhotoMouseAdapter implements ActionListener {
 
 
	private JButton jButton;
	private Camera camera;
 
 
	public TakePhotoMouseAdapter(JButton jButton, Camera camera) {
		this.jButton = jButton;
		this.camera = camera;
	}
 
 
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("拍照");
		
		// 修改显示
		if (camera.getState()) {
			jButton.setText("继续拍照");
			// 暂停拍照
			camera.setState(false);
		} else {
			jButton.setText("拍照");
			// 继续拍照
			camera.setState(true);
		}
	}
}