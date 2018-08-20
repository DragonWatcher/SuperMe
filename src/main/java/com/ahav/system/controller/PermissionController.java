package com.ahav.system.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.Permission;
import com.ahav.system.service.PermissionService;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiOperation;
/**
 * 
 * @author wxh
 *
 */
@RestController
@RequestMapping("/syspermission")
public class PermissionController {
	
	public static final transient Logger log = LoggerFactory
            .getLogger(PermissionController.class);
 
	@Autowired
	private PermissionService perService;
	
	/**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    @ResponseBody
    public Object unauth() {
    	System.out.println("当前请求未登录");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("code", "1000000");
        map.put("msg", "未登录");
        return map;
    }
	
	/**
	 * 自测通过
	 * 查询权限页面所有权限
	 * @return
	 */
	@ApiOperation(value = "获取权限", notes = "角色管理页面所有权限信息")
	@GetMapping("/permissions")
	public JSONObject permissions(){
		JSONObject jo = new JSONObject();
		List<Permission> pers = perService.findPermissions();
		if(pers != null){
			jo.put("permissions", pers);
			jo.put("code", HttpStatus.OK.value());
		}else{
			jo.put("code", HttpStatus.BAD_REQUEST.value());
		}
		return jo;
	}
}
