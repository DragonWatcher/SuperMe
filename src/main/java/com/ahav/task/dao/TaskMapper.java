package com.ahav.task.dao;

import org.apache.ibatis.annotations.Mapper;
import com.ahav.task.entity.Task;

@Mapper
public interface TaskMapper {
    int deleteByPrimaryKey(String taskId);

    int insert(Task record);

    int insertSelective(Task record);

    Task selectByPrimaryKey(String taskId);

    int updateByPrimaryKeySelective(Task record);

    int updateByPrimaryKey(Task record);
}