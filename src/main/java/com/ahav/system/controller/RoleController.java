package com.ahav.system.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.Permission;
import com.ahav.system.entity.Role;
import com.ahav.system.entity.User;
import com.ahav.system.service.PermissionService;
import com.ahav.system.service.RoleService;
import com.ahav.system.service.UserService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
/**
 * 角色管理的controller
 * @author wxh
 *
 */
@RestController
@RequestMapping("/sysrole")
public class RoleController {

    public static final transient Logger log = LoggerFactory
            .getLogger(RoleController.class);

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private PermissionService perService;

    
    /**
     * 自测通过
     * 添加角色的方法
     * @param role
     * @return
     */
    @ApiOperation(value = "添加角色", notes = "添加角色，只需要传一个参数：chinaRole(角色名称)")
    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public JSONObject createRole(@RequestBody String params){
    	JSONObject jo = new JSONObject();
    	try {
			JSONArray array = JSONArray.parseArray(params);
			List<Role> parseArray = JSONObject.parseArray(params, Role.class);
			for(int i=0; i<parseArray.size(); i++){
				if("".equals(parseArray.get(i).getRoleId()) || parseArray.get(i).getRoleId()==null){
					//没有roleId，说明是新增角色
					roleService.addRole(parseArray.get(i));
				}else{
					//有roleId，说明是更新角色名称
					roleService.updateRole(parseArray.get(i));
				}
			}
			jo.put("code", HttpStatus.OK.value());
		} catch (Exception e) {
			jo.put("code", HttpStatus.BAD_REQUEST.value());
		}
//        boolean addRole = roleService.addRole(role);
//        if(addRole){
//        	jo.put("code", org.springframework.http.HttpStatus.OK.value());
//        }else{
//        	jo.put("code", HttpStatus.BAD_REQUEST.value());
//        }
        return jo;
    }
    
    /**
     * 自测通过
     * 删除角色的方法，删除角色同时解除角色与用户的绑定关系
     * @param roleId
     * @return
     */
    @ApiOperation(value = "删除角色", notes = "删除角色,使用restful风格将参数roleId拼接在url后面")
    @ApiImplicitParams({
    	@ApiImplicitParam(name = "roleId", value = "角色id", required = true, dataType = "integer")
    })
    @DeleteMapping(value = "/roles/{roleId}")
    public JSONObject delRole(@PathVariable Integer roleId){
    	JSONObject jo = new JSONObject();
    	boolean deleteRoleById = roleService.deleteRoleById(roleId);
    	if(deleteRoleById){
    		jo.put("code", HttpStatus.OK.value());
    	}else{
    		jo.put("code", HttpStatus.BAD_REQUEST.value());
    	}
		return jo;
    }
    
    /**
     * 自测通过
     * 修改角色名称的方法
     * @param role
     * @return
     */
//    @ApiOperation(value = "修改角色", notes = "修改角色，需要传两个参数：roleId(角色ID)、chinaRole(角色名称)")
//    @RequestMapping(value = "/roles", method = RequestMethod.PUT)
//    public JSONObject updateRole(@RequestBody Role role){
//        JSONObject jo = new JSONObject();
//        boolean updateRole = roleService.updateRole(role);
//        if(updateRole){
//        	jo.put("code", HttpStatus.OK.value());
//    	}else{
//    		jo.put("code", HttpStatus.BAD_REQUEST.value());
//    	}
//        return jo;
//    }
    
    /**
     * 查询所有角色的方法
     * @return
     */
    @ApiOperation(value = "查询角色", notes = "查询角色:查询所有角色，不需要参数")
    @GetMapping(value = "/roles")
    public JSONObject findRoles(){
    	JSONObject jo = new JSONObject();
    	//查询sys_role表中所有角色
    	List<Role> roleList = roleService.findRoles();
    	if(roleList != null){
    		jo.put("code", HttpStatus.OK);
        	jo.put("roles", roleList);
        	//遍历角色结果集，查找每个角色对应的用户集合、权限集合
        	for(int i=0; i<roleList.size(); i++){
        		//查询角色对应用户
        		List<User> users = userService.findUsersByRoleId(roleList.get(i).getRoleId());
        		roleList.get(i).setUsers(users);
        		//查询角色对应权限
        		List<Permission> pers = perService.findPermissionByRoleId(roleList.get(i).getRoleId());
        		roleList.get(i).setPermissions(pers);
        	}
    	}else{
    		jo.put("code", HttpStatus.BAD_REQUEST.value());
    	}
		return jo;
    }
}
