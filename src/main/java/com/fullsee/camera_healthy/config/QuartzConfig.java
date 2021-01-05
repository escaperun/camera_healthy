package com.fullsee.camera_healthy.config;

import com.fullsee.camera_healthy.task.SnapshotQuartzTask;
import org.quartz.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * quartz定时任务配置
 * @author fullsee
 */
@Configuration
public class QuartzConfig {

    @Bean
    public JobDetail PayQuartz() {
        return JobBuilder.newJob(SnapshotQuartzTask.class).withIdentity("SnapshotQuartzTask").storeDurably().build();
    }

    @Bean
    public Trigger CallPayQuartzTaskTrigger() {
        //10秒执行一次
        //创建触发器
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                .withIntervalInSeconds(30)
                .repeatForever();
        //cron方式，每天定时执行一次
        //这些星号由左到右按顺序代表 ：*    *    *    *    *    *   *
        //格式：                 [秒] [分] [小时] [日] [月] [周] [年]
        return TriggerBuilder.newTrigger().forJob(PayQuartz())
                .withIdentity("SnapshotQuartzTask")
//                .withSchedule(CronScheduleBuilder.cronSchedule("0 0 17 * * ?"))
                .withSchedule(scheduleBuilder)
                .build();
    }
}
