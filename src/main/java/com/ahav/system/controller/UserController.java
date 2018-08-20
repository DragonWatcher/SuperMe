package com.ahav.system.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.entity.User;
import com.ahav.system.service.LoginService;
import com.ahav.system.service.UserService;
import com.ahav.system.util.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

/**
 * 用户请求控制器
 * <br>类名：UserController<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午10:42:57<br>
 */
@Api("成员管理：牟昊天")
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserService userService;

    @ApiIgnore
    @ApiOperation(value = "测试是否已登录")
    @GetMapping("/test")
    public String test() {
        // 测试登录登出前后的状态变化
        return "登录状态...";
    }
    
    /**
     * 登录
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午9:32:05<br>
     */
    @ApiOperation(value = "用户登录", notes = "用户名密码以formdata表单提交。可登陆用户名密码：mht,123456")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "username", paramType = "form"),
            @ApiImplicitParam(name = "password", paramType = "form") })
    @PostMapping("/login")
    public SystemResult login(@FormParam("username") String username, @FormParam("password") String password, HttpServletResponse response) {
        logger.info("请求登录...");
        // 登录测试
        return loginService.login(username, password, response);
    }

    /**
     * 登出
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午9:32:11<br>
     */
    @ApiOperation(value = "用户登出")
    @GetMapping("/logout")
    public SystemResult logout() {
        logger.info("用户登出...");
        return loginService.logout();
    }
    
    /**
     * 用户账号查重
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午11:42:01<br>
     */
    @ApiOperation(value = "用户名查重")
    @GetMapping("/username/{username}")
    public SystemResult checkUsername(@PathVariable String username) {
        return userService.checkUsername(username);
    }
    
    /**
     * 添加用户
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午11:41:29<br>
     */
    @ApiOperation(value = "添加用户")
    @PostMapping
//    @RequiresRoles("admin") TODO:超级管理员角色拦截
    public SystemResult createUser(@RequestBody User user) {
        return userService.createOrUpdUser(user);
    }
    
    /**
     * 更新用户
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:52:28<br>
     */
    @ApiOperation(value = "更新用户")
    @PutMapping
    public SystemResult updateUser(@RequestBody User user) {
        return userService.createOrUpdUser(user);
    }
    
    /**
     * 重置用户密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午6:19:34<br>
     */
    @ApiOperation(value = "重置密码")
    @PutMapping("/{userId}")
    public SystemResult resetPassword(@PathVariable Integer userId) {
        return userService.resetPassword(userId);
    }
    /**
     * 查找成员
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-上午9:58:19<br>
     */
    @ApiOperation(value = "查询用户列表")
    @GetMapping
    public SystemResult selectUsers(@RequestParam(defaultValue = SystemConstant.FIRST_PAGE) Integer pageNum,
            @RequestParam(defaultValue = SystemConstant.PAGE_SIZE) Integer pageSize, Integer roleId, Integer deptId,
            String username) {
        return userService.selectUsers(pageNum, pageSize, roleId, deptId, username);
    }
    
    /**
     * 指定用户id查询
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-上午10:02:13<br>
     */
    @ApiOperation(value = "id查找用户")
    @GetMapping("/{userId}")
    public SystemResult selectUser(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }
    
    /**
     * 删除用户
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:54:10<br>
     */
    @ApiOperation(value = "删除用户")
    @DeleteMapping("/{userId}")
    public SystemResult deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }
    
    /**
     * 获得当前用户的简单信息
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午9:05:40<br>
     */
    @ApiOperation(value = "获得当前用户")
    @GetMapping("/current")
    public SystemResult getCurrentUser() {
        return userService.getCurrentUser();
    }
    
    /**
     * 校验用户原密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午8:51:47<br>
     */
    @ApiOperation(value = "校验用户原密码")
    @PostMapping("/{password}")
    public SystemResult checkPassword(@PathVariable String password) {
        return userService.checkPassword(password);
    }
    
    /**
     * 修改用户密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午9:17:11<br>
     */
    @ApiOperation(value = "修改用户密码")
    @PutMapping("/password")
    public SystemResult updatePassword(@RequestBody User user) {
        return userService.updatePassword(user);
    }
    
    @ApiOperation(value = "预订人姓名模糊查询")
    @GetMapping("/truename/{trueName}")
    public SystemResult getUserByTrueName(@PathVariable String trueName) {
        return userService.getUserByTrueName(trueName);
    }
}
