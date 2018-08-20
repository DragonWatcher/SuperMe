package com.ahav.system.service;

import java.util.List;

import com.ahav.system.entity.Role;

public interface RoleService {

    public boolean addRole(Role role);

	public boolean deleteRoleById(Integer roleId);

	public boolean updateRole(Role role);

	public List<Role> findRoles();
	
	public List<Role> getRolesByUserId(Integer userId);

}
