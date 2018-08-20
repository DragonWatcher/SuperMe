package com.ahav.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahav.system.dao.RoleMapper;
import com.ahav.system.dao.UserRoleDao;
import com.ahav.system.entity.Role;
import com.ahav.system.service.RoleService;
@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleDao;
    
    @Autowired
    private UserRoleDao userRoleDao;

    public boolean addRole(Role role){
    	//除了系统自带的三个角色不允许删除，添加的其他角色均设置为可删除状态：enableDelete=true
    	role.setEnableDelete(true);
        int insert = roleDao.insert(role);
        if(insert == 1){
        	return true;
        }
        return false;
    }

	public boolean deleteRoleById(Integer roleId) {
		// 根据角色ID删除角色
		int deleteByPrimaryKey = roleDao.deleteByPrimaryKey(roleId);
		if(deleteByPrimaryKey == 1){
			//删除角色的同时删除关联表以及角色对应的用户User也被删除
			userRoleDao.deleteUserRole(roleId);
			return true;
		}
		return false;
	}

	public boolean updateRole(Role role) {
		// 因为参数role中不携带角色是否可删除信息，所以先根据角色ID查找到角色role1，然后把role1中的enableDelete的值赋给role，把role的信息更新到数据库
		Role role1 = roleDao.selectByPrimaryKey(role.getRoleId());
		role.setEnableDelete(role1.getEnableDelete());
		int updateByPrimaryKey = roleDao.updateByPrimaryKey(role);
		if(updateByPrimaryKey == 1){
			return true;
		}
		return false;
	}

	public List<Role> findRoles() {
		List<Role> roles = roleDao.selectRoles();
		return roles;
	}

	@Override
	public List<Role> getRolesByUserId(Integer userId) {
		//通过userId查找sys_user_role中间表，查到对应roleId
		Integer roleId = userRoleDao.findRoleId(userId);
		//根据roleId查找到角色
		Role role = roleDao.selectByPrimaryKey(roleId);
		//牟昊天是用集合接收的，所以把结果封装到一个集合中
		List<Role> roles = new ArrayList<>();
		roles.add(role);
		return roles;
	}

}
