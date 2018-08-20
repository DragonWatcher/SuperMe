package com.ahav.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ahav.system.entity.Permission;
import com.ahav.system.entity.Role;
@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Integer roleId);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Integer roleId);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
    
    Role findByUserId(Integer userId);

	List<Role> selectRoles();

	List<Permission> findPermissionsByRoleId(Integer roleId);
}