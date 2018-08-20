package com.ahav.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ahav.system.entity.Permission;
@Mapper
public interface PermissionMapper {
    int deleteByPrimaryKey(Integer permissionId);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Integer permissionId);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);

    List<Permission> selectPermissions();

    List<Permission> selectPermissionByPermissionIds(List perIds);
}