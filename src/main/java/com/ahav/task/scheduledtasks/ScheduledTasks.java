package com.ahav.task.scheduledtasks;

import java.text.SimpleDateFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import com.ahav.task.controller.TaskController;
import com.ahav.task.service.TaskService;

@Configuration		//证明这个类是一个配置文件 
@EnableScheduling		//打开quartz定时器总开关
public class ScheduledTasks {
	
	public static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
	
	@Autowired
	public TaskService taskService;

	/**
	 * 每小时执行一次的定时任务，目的是检测任务是否超时
	 */
	@Scheduled(cron = "0 0 0/1 * * *")  
	public void timer(){ 
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		long currentTimeMillis = System.currentTimeMillis();
		String publishTime  = format.format(currentTimeMillis);
		logger.info("---------------------每小时执行一次定时任务                     " + publishTime + "---------------------");
		System.out.println("---------------------每小时执行一次定时任务                     " + publishTime + "---------------------");
		//以下是定时任务逻辑
		taskService.updateTasks();
	}
}
