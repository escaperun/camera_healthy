package com.fullsee.camera_healthy.controller;


import com.fullsee.camera_healthy.util.CameraUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Description:
 * @author: fullsee
 * @date: 2020/10/29 13:26
 */
@RestController
public class CameraController {

    @GetMapping("photo")
    public ResponseEntity<String> getPhoto(){
//        CameraUtil.jButton.doClick();
        CameraUtil.take_photo.doClick();
        CameraUtil.save_photo.doClick();
        CameraUtil.take_photo.doClick();
        return new ResponseEntity<>("操作成功", HttpStatus.OK);
    }
}
