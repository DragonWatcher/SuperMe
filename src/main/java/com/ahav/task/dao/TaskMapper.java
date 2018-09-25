package com.ahav.task.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ahav.task.entity.Query;
import com.ahav.task.entity.Task;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(String taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);

	List<Task> findTasks(@Param("params") Map query);

	Integer findCounts(@Param("params") Map query);

	void updateTasks();

//	List<Task> findTasks(Integer pageSize, Integer currentPage, String orderBy, String publisher, String surveyor,
//			String executor, String taskStatus, String acceptanceResults, String relatedMeetings, String endTime1,
//			String endTime2, String taskName, String publishTime1, String publishTime2, String keyWord);
}