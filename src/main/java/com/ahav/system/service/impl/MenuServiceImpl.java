package com.ahav.system.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahav.system.dao.MenuMapper;
import com.ahav.system.dao.RoleMenuMapper;
import com.ahav.system.dao.UserRoleDao;
import com.ahav.system.entity.Menu;
import com.ahav.system.entity.RoleMenu;
import com.ahav.system.service.MenuService;
@Service
public class MenuServiceImpl implements MenuService {
	
	@Autowired
	private MenuMapper menuMapper;
	
	@Autowired
	private UserRoleDao userRoleDao;
	
	@Autowired
	private RoleMenuMapper roleMenuService;

	@Override
	public List<Menu> findMenus(Integer userId) {
		// 根据userId查找对应角色
		int roleId = userRoleDao.findRoleId(userId);
		//根据roleId查找对应菜单集合
		List<RoleMenu> findMenuIds = roleMenuService.findMenuIds(roleId);
		List menuIds = new ArrayList<>();
		for(int i=0; i<findMenuIds.size(); i++){
			menuIds.add(findMenuIds.get(i).getMenuId());
		}
		//根据菜单ids查找到菜单信息集合
		List<Menu> menus = findMenusByIds(menuIds);
		//封装返回结果
		return menus;
	}

	@Override
	public List<Menu> findMenusByIds(List menuIds) {
		List<Menu> menus1 = menuMapper.findMenusByIds1(menuIds);//查找一级菜单
		List<Menu> menus2 = menuMapper.findMenusByIds2(menuIds);//查找二级菜单
		//找到二级菜单与一级菜单的对应关系
		for(int i=0; i<menus1.size(); i++){
			List<Menu> menus3 = new ArrayList<>();
			for(int j=0; j<menus2.size(); j++){
				if(menus1.get(i).getMenuId().equals(menus2.get(j).getParentMenuId())){
					menus3.add(menus2.get(j));
				}
			}
			menus1.get(i).setChildren(menus3);
		}
		return menus1;
	}

}
