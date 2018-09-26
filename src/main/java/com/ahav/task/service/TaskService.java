package com.ahav.task.service;

import java.util.List;

import com.ahav.task.entity.Query;
import com.ahav.task.entity.Task;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;

public interface TaskService {

	JSONObject addTask(Task task);

	List<Task> findTasks(Query query);
	PageInfo findTasksPages(Query query);

	Integer findCounts(Query query);

	boolean deleteTask(String taskId);

	boolean updateTask(Task task);

	void updateTasks();

	Task findByTaskId(String taskId);

//	List<Task> findTasks(Integer pageSize, Integer currentPage, String orderBy, String publisher, String surveyor,
//			String executor, String taskStatus, String acceptanceResults, String relatedMeetings, String endTime1,
//			String endTime2, String taskName, String publishTime1, String publishTime2, String keyWord);

}
