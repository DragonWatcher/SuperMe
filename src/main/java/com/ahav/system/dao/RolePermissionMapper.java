package com.ahav.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ahav.system.entity.RolePermissionKey;
@Mapper
public interface RolePermissionMapper {
    int deleteByPrimaryKey(RolePermissionKey key);

    int insert(RolePermissionKey record);

    int insertSelective(RolePermissionKey record);

    List<RolePermissionKey> selectPermissionIds(Integer roleId);

	int deleteByRoleId(Integer roleId);
}