package com.ahav.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.task.entity.Task;
import com.ahav.task.service.TaskService;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/task")
public class TaskController {

	public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	public TaskService taskService;
	
	@RequestMapping(value = "/tasks", produces = "application/json;utf-8", method = RequestMethod.POST)
	@ApiOperation(value = "发布任务", notes = "发布任务接口")
	@ApiImplicitParams(value = {
		@ApiImplicitParam(name = "surveyor", value = "验收人", required = true, dataType = "String"),
		@ApiImplicitParam(name = "startTime", value = "验收人", required = true, dataType = "String"),
		@ApiImplicitParam(name = "endTime", value = "验收人", required = true, dataType = "String"),
		@ApiImplicitParam(name = "relatedMeetings", value = "验收人", required = true, dataType = "String"),
		@ApiImplicitParam(name = "executor", value = "验收人", required = true, dataType = "String"),
		@ApiImplicitParam(name = "taskName", value = "验收人", required = true, dataType = "String"),
		@ApiImplicitParam(name = "taskDescribe", value = "验收人", required = true, dataType = "String")
	})
	public JSONObject addTask(@RequestBody Task task){
		System.out.println("发布任务");
		JSONObject jo = new JSONObject();
		boolean b = taskService.addTask(task);
		return jo;
	}
}
