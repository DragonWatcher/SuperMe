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
public class SchedulingConfig implements Runnable{
    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;

    public void getToken() {
        //调用加载pub模板方法
        meetingDetailsServiceImpl.loadPubTemplateCon();
    }


    @Scheduled(fixedRate = 4000) //指定定时任务每4秒钟执行一次
    @Override
    public synchronized void run() {
//        getToken();
    }
}
