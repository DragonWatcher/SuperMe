package com.ahav.system.controller;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.FormParam;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.entity.User;
import com.ahav.system.service.LoginService;
import com.ahav.system.service.UserService;
import com.ahav.system.util.SystemConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 用户请求控制器
 * <br>类名：UserController<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午10:42:57<br>
 */
@Api("成员管理：牟昊天")
@CrossOrigin(allowedHeaders = "*")
@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private LoginService loginService;
    
    @Autowired
    private UserService userService;

    /**
     * 登录
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午9:32:05<br>
     */
    @ApiOperation(value = "用户登录", notes = "Meeting登录采用form表单提交用户名和密码.可登陆用户名密码：admin,12345678")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(required = true, name = "username", value = "用户名", paramType = "form", dataType = "String"),
            @ApiImplicitParam(required = true, name = "password", value = "登录密码", paramType = "form", dataType = "String") })
    @PostMapping("/login")
    public SystemResult login(@FormParam("username") String username, @FormParam("password") String password) {
        logger.info("请求登录...");
        // 登录测试
        return loginService.login(username, password);
    }

    /**
     * 登出
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午9:32:11<br>
     */
    @ApiOperation(value = "用户登出", notes = "用户登出")
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
    @ApiOperation(value = "用户名查重", notes = "用户账号查重是为了在添加用户时，页面异步调用，如果有重复用户名，则提示重复信息")
    @ApiImplicitParam(name = "username", value = "待验重的用户名", required = true)
    @GetMapping("/username/{username}")
    public SystemResult checkUsername(@PathVariable String username) {
        return userService.checkUsername(username);
    }
    
    /**
     * 添加用户
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午11:41:29<br>
     */
    @ApiOperation(value = "添加用户", notes = "单个用户添加，<br>"
            + "格式：{\"dept\":{\"deptId\":1},\"role\":{\"roleId\":3},\"trueName\":\"小明\",\"username\":\"mfy1\"}<br>"
            + "传入的User对象封装了部门及角色对象，页面只需要传入上述格式中的字段即可")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true)
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
    @ApiOperation(value = "更新用户", notes = "成员编辑页面，修改：角色、部门、姓名、账号，四个信息，传入的结构和添加用户功能雷同")
    @ApiImplicitParam(name = "user", value = "用户对象", required = true)
    @PutMapping
    public SystemResult updateUser(@RequestBody User user) {
        return userService.createOrUpdUser(user);
    }
    
    /**
     * 重置用户密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午6:19:34<br>
     */
    @ApiOperation(value = "重置密码", notes = "系统内置一个初始密码，页面点击重置密码后，直接传入用户ID，即可还原到系统原始密码")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @PutMapping("/{userId}")
    public SystemResult resetPassword(@PathVariable Integer userId) {
        return userService.resetPassword(userId);
    }
    /**
     * 查找成员
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-上午9:58:19<br>
     */
    @ApiOperation(value = "查询用户列表", notes = "查找成员，通过筛选条件:角色id，部门id，用户名进行查找")
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "pageNum", value = "页号", required = false, defaultValue = SystemConstant.FIRST_PAGE),
            @ApiImplicitParam(name = "pageSize", value = "条数", required = false,defaultValue = SystemConstant.PAGE_SIZE),
            @ApiImplicitParam(name = "roleId", value = "角色ID", required = false),
            @ApiImplicitParam(name = "deptId", value = "部门ID", required = false),
            @ApiImplicitParam(name = "username", value = "用户名", required = false)
    })
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
    @ApiOperation(value = "id查找用户", notes = "根据用户ID精确查找")
    @ApiImplicitParam(name = "userId", value = "用户id", required = true)
    @GetMapping("/{userId}")
    public SystemResult selectUser(@PathVariable Integer userId) {
        return userService.getUserById(userId);
    }
    
    /**
     * 删除用户
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:54:10<br>
     */
    @ApiOperation(value = "删除用户", notes = "删除单条用户，需要传入userId")
    @ApiImplicitParam(name = "userId", value = "用户ID", required = true)
    @DeleteMapping("/{userId}")
    public SystemResult deleteUser(@PathVariable Integer userId) {
        return userService.deleteUser(userId);
    }
    
    /**
     * 获得当前用户的简单信息
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午9:05:40<br>
     */
    @ApiOperation(value = "获得当前用户", notes = "获取当前用户简要信息")
    @GetMapping("/current")
    public SystemResult getCurrentUser() {
        return userService.getCurrentUser();
    }
    
    /**
     * 校验用户原密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午8:51:47<br>
     */
    @ApiOperation(value = "校验用户原密码", notes = "在用户自己修改密码时，需要异步调用此接口进行用户原密码校验")
    @ApiImplicitParam(name = "password", value = "用户原来密码", required = true)
    @PostMapping("/{password}")
    public SystemResult checkPassword(@PathVariable String password) {
        return userService.checkPassword(password);
    }
    
    /**
     * 修改用户密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午9:17:11<br>
     */
    @ApiOperation(value = "修改用户密码", notes = "修改密码需要传入User对象的username，trueName，pwd，这三个参数用户生成用户的密码和salt")
    @ApiImplicitParam(required = true, name = "user", value = "用户对象")
    @PutMapping("/password")
    public SystemResult updatePassword(@RequestBody User user) {
        return userService.updatePassword(user);
    }
    
    @ApiOperation(value = "预订人姓名模糊查询", notes = "预订人姓名模糊查询")
    @ApiImplicitParam(name = "trueName", value = "真实姓名", required = true)
    @GetMapping("/truename/{trueName}")
    public SystemResult getUserByTrueName(@PathVariable String trueName) {
        return userService.getUserByTrueName(trueName);
    }
    
    @ApiOperation(value = "设置当前用户的界面颜色", notes = "put请求，传入color字符串")
    @ApiImplicitParam(name = "color", value = "用户选定的页面颜色", required = true)
    @PutMapping("/colors")
    public SystemResult setUserColor(@RequestParam(required = true) String color) {
        return userService.updUserColor(color);
    }
    
    @ApiOperation(value = "设置用户头像", notes = "设置当前用户头像")
    @PutMapping("/profiles")
    public SystemResult setUserProfile(@RequestParam(required = true) MultipartFile profile) {
        return userService.updUserProfile(profile);
    }
}
