package com.ahav.task.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahav.task.dao.TaskMapper;
import com.ahav.task.entity.Query;
import com.ahav.task.entity.Task;
import com.ahav.task.service.TaskService;
import com.ahav.util.GeneralUtils;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	public TaskMapper taskDao;

	@Override
	public boolean addTask(Task task) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
			//获取当前系统时间
			long currentTimeMillis = System.currentTimeMillis();
			String publishTime  = format.format(currentTimeMillis);
			task.setPublishTime(publishTime);
//		//获取当前登录用户（这个参数由前端传递过来）
//		User principal = (User) SecurityUtils.getSubject().getPrincipal();
//		task.setPublisher(principal.getUsername());
			//设置任务id：由4位英文字母＋6位数字组成
			String taskId = GeneralUtils.getString();
			task.setTaskId(taskId);
			//设置任务初始状态
			task.setTaskStatus("进行中");
			int insert = taskDao.insert(task);
			// TODO Auto-generated catch block
			//发布任务：插入数据库的同时发送邮件
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public List<Task> findTasks(Query query) {
		Map map = queryToMap(query);
		List<Task>  tasks = taskDao.findTasks(map);
		return tasks;
	}
	
	//反射工具方法
	public Map queryToMap(Query query){
		Map map = new HashMap();
		Class<?> clz = query.getClass();
		Field[] declaredFields = clz.getDeclaredFields();
		for(int i=0; i<declaredFields.length; i++){
			Method method;
			try {
				method = clz.getMethod("get" + getMethodName(declaredFields[i].getName()));
				Object invoke = method.invoke(query);
				//如果当前字段为null或者空字符串，则不加入参数集合
				if(invoke != null && !"".equals(invoke)){
					map.put(declaredFields[i].getName(), invoke);
				}
//				System.out.println("属性：" + declaredFields[i].getName() + ",值是：" + invoke);
//				System.out.println(map.toString());
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return map;
	}
	
	// 把一个字符串的第一个字母大写、效率是最高的、
	 private static String getMethodName(String fildeName) throws Exception{
	  byte[] items = fildeName.getBytes();
	  items[0] = (byte) ((char) items[0] - 'a' + 'A');
	  return new String(items);
	 }

	@Override
	public Integer findCounts(Query query) {
		Map map = queryToMap(query);
		Integer findCounts = taskDao.findCounts(map);
		return findCounts;
	}

	@Override
	public boolean deleteTask(String taskId) {
		int flag = taskDao.deleteByPrimaryKey(taskId);
		if(flag==1){
			return true;
		}
		return false;
	}

	@Override
	public boolean updateTask(Task task) {
		Task t = taskDao.selectByPrimaryKey(task.getTaskId());
		t.setAcceptanceResults(task.getAcceptanceResults());
		int b = taskDao.updateByPrimaryKey(t);
		if(b == 1){
			return true;
		}
		return false;
	}

//	@Override
//	public List<Task> findTasks(Integer pageSize, Integer currentPage, String orderBy, String publisher,
//			String surveyor, String executor, String taskStatus, String acceptanceResults, String relatedMeetings,
//			String endTime1, String endTime2, String taskName, String publishTime1, String publishTime2,
//			String keyWord) {
//		List<Task>  tasks = taskDao.findTasks(pageSize, currentPage, orderBy, publisher, surveyor, executor, taskStatus, acceptanceResults, relatedMeetings, endTime1, endTime2, taskName, publishTime1, publishTime2, keyWord);
//		return tasks;
//	}


//	public static void main(String[] args){
//		SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
//		//获取当前系统时间
//		long currentTimeMillis = System.currentTimeMillis();
//		String format2 = format.format(currentTimeMillis);
//		System.out.println(format2);
//	}
}
