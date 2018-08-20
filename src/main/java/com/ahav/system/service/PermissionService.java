package com.ahav.system.service;

import java.util.List;

import com.ahav.system.entity.Permission;

public interface PermissionService {

	List<Permission> findPermissions();

	List<Permission> findPermissionByRoleId(Integer roleId);

}
