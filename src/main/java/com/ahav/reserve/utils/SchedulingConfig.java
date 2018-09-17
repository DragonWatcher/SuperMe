package com.ahav.reserve.utils;

import com.ahav.reserve.service.IMeetingDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.security.PublicKey;


@Configuration
@EnableScheduling
@Async
public class SchedulingConfig {
    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;

    /*@Scheduled(fixedRate = 4000) //指定定时任务每4秒钟执行一次
    public void loadPubTemplateCon() {
        //调用加载pub模板方法
        meetingDetailsServiceImpl.loadPubTemplateCon();
    }*/

    @Scheduled(cron = "0 0 23 * * ?") //每天23点执行一次
    public void getToken() {
        //调用
        CreateTokenDemo.CreateToken();
    }

}
