package com.ahav.task.controller;

import java.util.List;

import javax.ws.rs.FormParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.task.entity.Query;
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
			@ApiImplicitParam(name = "publisher", value = "发布人", required = true, dataType = "String"),
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
		jo.put("msg", b);
		return jo;
	}
	
	/**要不要加一个参数标识是哪个页面触发呢？？？？？？？？？？？？？？？
	 * 任务模块条件查询
	 * @param query.getWhichPage():表示请求来自哪一页
	 * 
	 * 
	 * @param pageSize:每页显示条数，默认值是10
	 * @param currentPage:当前页，默认值是1
	 * @param orderBy:排序方式，默认值是先开始优先
	 * @param publisher:发布人，（发布历史）页面触发时（必须）携带此参数，模糊查询
	 * @param surveyor:验收人，（任务验收）页面触发时（必须）携带此参数，模糊查询
	 * @param executor:执行人，（我的任务）页面触发时（必须）携带此参数，模糊查询
	 * @param taskStatus:任务状态，分为：进行中、待验收、超时未确认、已验收
	 * @param acceptanceResults:验收结果，分为：完成、部分完成、未完成
	 * @param relatedMeetings:相关会议
	 * @param endTime1:任务结束时间
	 * @param endTime2:任务结束时间
	 * @param taskName:任务名称
	 * @param publishTime1:默认从系统使用最初时间到查寻当天
	 * @param publishTime2:默认从系统使用最初时间到查寻当天
	 * @param keyWord:任务关键字，查询目标为任务描述和任务名称
	 * @return
	 */
	@ApiOperation(value = "条件查询", notes = "任务模块多条件查询接口,whichPage为必传参数  代表是哪个页面,取值为1、2、3,1代表发布历史页面,2代表任务验收页面,3代表我的任务页面；"
			+ "当whichPage=1时，publisher为必传参数；当whichPage=2时，surveyor为必传参数；当whichPage=3时，executor为必传参数")
	@ApiImplicitParams(value = {
			@ApiImplicitParam(name = "pageSize", value = "每页条数", required = false, dataType = "String"),
		@ApiImplicitParam(name = "currentPage", value = "当前页数", required = false, dataType = "String"),
		@ApiImplicitParam(name = "orderBy", value = "排序", required = false, dataType = "String"),
		@ApiImplicitParam(name = "publisher", value = "发布人", required = false, dataType = "String"),
		@ApiImplicitParam(name = "surveyor", value = "验收人", required = false, dataType = "String"),
		@ApiImplicitParam(name = "executor", value = "执行人", required = false, dataType = "String"),
		@ApiImplicitParam(name = "taskStatus", value = "任务状态", required = false, dataType = "String"),
		@ApiImplicitParam(name = "acceptanceResults", value = "验收结果", required = false, dataType = "String"),
		@ApiImplicitParam(name = "relatedMeetings", value = "相关会议", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime1", value = "结束时间1", required = false, dataType = "String"),
		@ApiImplicitParam(name = "endTime2", value = "结束时间2", required = false, dataType = "String"),
		@ApiImplicitParam(name = "taskName", value = "任务名称", required = false, dataType = "String"),
		@ApiImplicitParam(name = "publishTime1", value = "发布时间1", required = false, dataType = "String"),
		@ApiImplicitParam(name = "publishTime2", value = "发布时间2", required = false, dataType = "String"),
		@ApiImplicitParam(name = "keyWord", value = "任务关键字", required = false, dataType = "String")
	})
	@RequestMapping(value = "/searchtasks", produces = "application/json;utf-8", method = RequestMethod.POST)
	public JSONObject getTasks(@RequestBody Query query){
		//计算mysql查询开始行
		query.setStart();
		JSONObject jo = new JSONObject();
		List<Task> tasks = null;
		if(query.getWhichPage() != null && "1".equals(query.getWhichPage())){
			//发布历史
			if(query.getPublisher() != null && !"".equals(query.getPublisher())){
				tasks = taskService.findTasks(query);
			}else{
				jo.put("msg", "publisher不合法");
			}
		}else if(query.getWhichPage() != null && "2".equals(query.getWhichPage())){
			//任务验收
			if(query.getSurveyor() != null && !"".equals(query.getSurveyor())){
				tasks = taskService.findTasks(query);
			}else{
				jo.put("msg", "surveyor不合法");
			}
		}else if(query.getWhichPage() != null && "3".equals(query.getWhichPage())){
			//我的任务
			if(query.getExecutor() != null && !"".equals(query.getExecutor())){
				tasks = taskService.findTasks(query);
			}else{
				jo.put("msg", "executor不合法");
			}
		}else{
			jo.put("msg", "whichPage不合法");
		}
		jo.put("tasks", tasks);
		//查询满足条件的总条数
		Integer counts = taskService.findCounts(query);
		Integer pages;
		if(counts / query.getPageSize() == 0){
			pages = counts / query.getPageSize();
		}else{
			pages = counts / query.getPageSize() + 1;
		}
		jo.put("pages", pages);
		return jo;
	}
	
	@RequestMapping(value = "/tasks/{taskIds}", produces = "application/json;utf-8", method = RequestMethod.DELETE)
	public JSONObject delTasks(@PathVariable Integer taskId){
		JSONObject jo = new JSONObject();
		boolean b = taskService.deleteTask(taskId);
		jo.put("msg", b);
		return jo;
	}
}
