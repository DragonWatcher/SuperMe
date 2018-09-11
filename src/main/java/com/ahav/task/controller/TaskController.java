package com.ahav.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.task.entity.Task;
import com.ahav.task.service.TaskService;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 任务模块相关接口
 * @author wxh
 *
 */
@RestController
@RequestMapping("/task")
public class TaskController {

	public static final Logger logger = LoggerFactory.getLogger(TaskController.class);
	
	@Autowired
	public TaskService taskService;
	
	/**
	 * 发布任务的接口：所有参数是必填项，不可为空
	 * @param task
	 * @return
	 */
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
	
	/**要不要加一个参数标识是哪个页面触发呢？？？？？？？？？？？？？？？
	 * 任务模块条件查询
	 * @param pageSize:每页显示条数，默认值是10
	 * @param currentPage:当前页，默认值是1
	 * @param orderBy:排序方式，默认值是先开始优先
	 * @param publisher:发布人，（发布历史）页面触发时（必须）携带此参数，模糊查询
	 * @param surveyor:验收人，（任务验收）页面触发时（必须）携带此参数，模糊查询
	 * @param executor:执行人，（我的任务）页面触发时（必须）携带此参数，模糊查询
	 * @param taskStatus:任务状态，分为：进行中、待验收、超时未确认、已验收
	 * @param acceptanceResults:验收结果，分为：完成、部分完成、未完成
	 * @param relatedMeetings:相关会议
	 * @param endTime:任务结束时间，拆分成两个
	 * @param taskName:任务名称
	 * @param publishTime:默认从系统使用最初时间到查寻当天，拆分成两个
	 * @param keyWord:任务关键字，查询目标为任务描述和任务名称
	 * @return
	 */
	@RequestMapping(value = "/tasks", produces = "application/json;utf-8", method = RequestMethod.GET)
	public JSONObject getTasks(@RequestParam(defaultValue="10") Integer pageSize,
			@RequestParam(defaultValue="1") Integer currentPage,
			@RequestParam(defaultValue="start_time asc") String orderBy,
			String publisher, String surveyor, String executor, String taskStatus, String acceptanceResults,
			String relatedMeetings, String endTime, String taskName, String publishTime, String keyWord
			){
		JSONObject jo = new JSONObject();
		
		return jo;
	}
}
