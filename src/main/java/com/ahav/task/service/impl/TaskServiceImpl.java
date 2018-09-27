package com.ahav.task.service.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class TaskServiceImpl implements TaskService{
	
	@Autowired
	public TaskMapper taskDao;

	@Override
	public JSONObject addTask(Task task) {
		JSONObject jo = new JSONObject();
		try {
			SimpleDateFormat format = new SimpleDateFormat("yy-MM-dd HH:mm");
			//获取当前系统时间,如：18-09-19 14:00，精确到小时；因为定时任务每小时执行一次
			long currentTimeMillis = System.currentTimeMillis();
			String publishTime  = format.format(currentTimeMillis);
			publishTime = publishTime.substring(0, publishTime.lastIndexOf(":")) + ":00";
			task.setPublishTime(publishTime);
//		//获取当前登录用户（这个参数由前端传递过来）
//		User principal = (User) SecurityUtils.getSubject().getPrincipal();
//		task.setPublisher(principal.getUsername());
			//设置任务初始状态
			task.setTaskStatus("进行中");
			Date start = format.parse(task.getStartTime());
			Date end = format.parse(task.getEndTime());
			int compareTo = start.compareTo(end);
			if(compareTo < 0){
				//start在end之前
				//判断执行人数，有几人就加入几条数据
				String executor = task.getExecutor();
				if(executor.indexOf(",") > 0){
					//说明executor里面包含","，即执行人有多个
					String[] split = executor.split(",");
					for(int i=0; i<split.length; i++){
						//设置任务id：由4位英文字母＋6位数字组成
						String taskId = GeneralUtils.getString();
						task.setTaskId(taskId);
						task.setExecutor(split[i]);
						int insert = taskDao.insert(task);
					}
				}else{
					//executor不包含逗号，说明执行人只有一个
					//设置任务id：由4位英文字母＋6位数字组成
					String taskId = GeneralUtils.getString();
					task.setTaskId(taskId);
					int insert = taskDao.insert(task);
				}
				jo.put("msg", true);
			}else{
				//start在end之后
				jo.put("message", "日期有误");
				jo.put("msg", false);
			}
			// TODO Auto-generated catch block
			//发布任务：插入数据库的同时发送邮件
			
		} catch (Exception e) {
			e.printStackTrace();
			jo.put("msg", false);
		}
		return jo;
	}

	@Override
	public List<Task> findTasks(Query query) {
		Map map = queryToMap(query);
		List<Task>  tasks = taskDao.findTasks(map);
		return tasks;
	}
	@Override
	public PageInfo findTasksPages(Query query) {
		PageHelper.startPage(query.getCurrentPage(), query.getPageSize()); //一定要放在查询之前
		Map map = queryToMap(query);
		List<Task>  tasks = taskDao.findTasks(map);
		PageInfo info = new PageInfo<>(tasks);
		return info;
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
		if(task.getTaskStatus() != null && !"".equals(task.getTaskStatus())){
			t.setTaskStatus(task.getTaskStatus());
		}
		if(task.getAcceptanceResults() != null && !"".equals(task.getAcceptanceResults())){
			t.setAcceptanceResults(task.getAcceptanceResults());
		}
		if(task.getAcceptanceEvaluate() != null && !"".equals(task.getAcceptanceEvaluate())){
			t.setAcceptanceEvaluate(task.getAcceptanceEvaluate());
		}
		if(task.getFinishResults() != null && !"".equals(task.getFinishResults())){
			t.setFinishResults(task.getFinishResults());
		}
		if(task.getFinishEvaluate() != null && !"".equals(task.getFinishEvaluate())){
			t.setFinishEvaluate(task.getFinishEvaluate());
		}
		int b = taskDao.updateByPrimaryKey(t);
		if(b == 1){
			return true;
		}
		return false;
	}

	@Override
	public void updateTasks() {
		taskDao.updateTasks();
	}

	@Override
	public Task findByTaskId(String taskId) {
		Task task = taskDao.selectByPrimaryKey(taskId);
		return task;
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
