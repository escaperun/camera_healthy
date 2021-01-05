package com.fullsee.camera_healthy;

import com.fullsee.camera_healthy.filter.CrosFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@SpringBootApplication
@EnableScheduling
public class CameraHealthyApplication {

    public static void main(String[] args) {
//        SpringApplication.run(CameraHealthyApplication.class, args);
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CameraHealthyApplication.class);
        builder.headless(false).run(args);
    }
    /**
     * 配置跨域访问的过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean registerFilter(){
        FilterRegistrationBean bean = new FilterRegistrationBean();
        bean.addUrlPatterns("/*");
        bean.setFilter(new CrosFilter());
        return bean;
    }


}
