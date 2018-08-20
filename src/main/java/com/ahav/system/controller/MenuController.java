package com.ahav.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.Menu;
import com.ahav.system.service.MenuService;
import com.ahav.system.util.CheckPermission;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 菜单管理的controller
 * @author wxh
 *
 */
@RestController
@RequestMapping("/sysmenu")
public class MenuController {

	 public static final transient Logger log = LoggerFactory
	            .getLogger(MenuController.class);
	 
	 @Autowired
	 MenuService menuService;
	 
	 /**
	  * 根据登录用户返回有权限的菜单
	  * @param userId
	  * @return
	  */
	 @ApiOperation(value="获取菜单", notes="根据登录用户返回有权限的菜单")
	 @ApiImplicitParams({
		 @ApiImplicitParam(name="userId", value="用户id", required=true, dataType="integer")
	 })
	 @GetMapping(value = "/menus")
	 public JSONObject findMenus(Integer userId){
		 boolean checkPermission = CheckPermission.checkPermission("hh:add:me");
		 System.out.println("有权限吗：" + checkPermission);
		 JSONObject jo = new JSONObject();
		 if(checkPermission){
			 List<Menu> findMenus = menuService.findMenus(userId);
			 if(findMenus != null){
				 jo.put("menus", findMenus);
				 jo.put("code", HttpStatus.OK.value());
			 }else{
				 jo.put("code", HttpStatus.BAD_REQUEST.value());
			 }
		 }else{
			 jo.put("code", HttpStatus.BAD_REQUEST.value());
			 jo.put("message", "没有权限");
		 }
		 return jo;
	 }

}
