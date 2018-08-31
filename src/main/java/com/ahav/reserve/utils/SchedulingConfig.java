package com.ahav.reserve.utils;

import com.ahav.reserve.service.IMeetingDetailsService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class SchedulingConfig {
    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;
    private final Logger logger = LoggerFactory.getLogger(getClass());
/*    @Resource
    private RobotRestServiceImpl robotRestService;*/
    @Scheduled(fixedRate = 4000) //指定定时任务每4秒钟执行一次
    public void getToken() {
        //调用加载pub模板方法
        //logger.info("getToken定时任务启动");
        meetingDetailsServiceImpl.loadPubTemplateCon();
    }
}
