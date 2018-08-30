package com.ahav.reserve.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Configuration
@EnableScheduling
public class SchedulingConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

/*    @Resource
    private RobotRestServiceImpl robotRestService;*/
    @Scheduled(cron="0 0/10 * * * ?")
    public void getToken() {
        //调用加载pub模板方法
        //logger.info("getToken定时任务启动");
        /*System.out.println("99999999999999999999999999999999999999999999999999999999");*/
    }
}
