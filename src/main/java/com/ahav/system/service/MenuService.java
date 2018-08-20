package com.ahav.system.service;

import java.util.List;

import com.ahav.system.entity.Menu;

public interface MenuService {

	List<Menu> findMenus(Integer userId);

	List<Menu> findMenusByIds(List menuIds);

}
