package com.ahav.task.service.impl;

import java.text.SimpleDateFormat;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahav.system.entity.User;
import com.ahav.task.dao.TaskMapper;
import com.ahav.task.entity.Task;
import com.ahav.task.service.TaskService;
import com.ahav.util.GeneralUtils;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	public TaskMapper taskDao;

	@Override
	public boolean addTask(Task task) {
		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
		//获取当前系统时间
		long currentTimeMillis = System.currentTimeMillis();
		String publishTime  = format.format(currentTimeMillis);
		//获取当前登录用户
		User principal = (User) SecurityUtils.getSubject().getPrincipal();
		task.setPublisher(principal.getUsername());
		task.setPublishTime(publishTime);
		//设置任务id：由4位英文字母＋6位数字组成
		String taskId = GeneralUtils.getString();
		task.setTaskId(taskId);
		//设置任务初始状态
		task.setTaskStatus("进行中");
		int insert = taskDao.insert(task);
		return false;
	}

//	public static void main(String[] args){
//		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//		//获取当前系统时间
//		long currentTimeMillis = System.currentTimeMillis();
//		String format2 = format.format(currentTimeMillis);
//		System.out.println(format2);
//	}
}
