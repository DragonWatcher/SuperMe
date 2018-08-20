package com.ahav.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ahav.system.entity.Menu;

@Mapper
public interface MenuMapper {
    int deleteByPrimaryKey(String menuId);

    int insert(Menu record);

    int insertSelective(Menu record);

    Menu selectByPrimaryKey(String menuId);

    int updateByPrimaryKeySelective(Menu record);

    int updateByPrimaryKey(Menu record);

    List<Menu> findMenusByIds1(List menuIds);
    
    List<Menu> findMenusByIds2(List menuIds);
}