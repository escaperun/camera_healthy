package com.fullsee.camera_healthy.task;

import com.fullsee.camera_healthy.fastdfs.FastDfsService;
import com.fullsee.camera_healthy.fastdfs.impl.FastFastDfsServiceImpl;
import com.fullsee.camera_healthy.util.CameraUtil;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @author: fullsee
 * @date: 2020/10/29 17:10
 */
@Component
public class InitCameraTask implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        CameraUtil.CameraInit();
    }
}
