package com.fullsee.camera_healthy.task;

import com.fullsee.camera_healthy.util.CameraUtil;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @Description: 抓拍任务
 * @author: fullsee
 * @date: 2020/11/25 13:31
 */
@Slf4j
public class SnapshotQuartzTask extends QuartzJobBean {

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        CameraUtil.take_photo.doClick();
        CameraUtil.save_photo.doClick();
        CameraUtil.take_photo.doClick();
        log.info("抓拍成功");
    }
}
