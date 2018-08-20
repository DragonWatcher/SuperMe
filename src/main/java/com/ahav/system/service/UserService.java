package com.ahav.system.service;

import java.util.List;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.entity.User;

/**
 * 系统用户服务
 * <br>类名：UserService<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午2:01:32<br>
 */
public interface UserService {
    /**
     * 根据用户id查找对应用户
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午4:23:38<br>
     * @param userId
     * @return
     */
    SystemResult getUserById(Integer userId);
    
    /**
     * 通过用户名获得账户信息
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-上午11:17:27<br>
     * @param username
     * @return
     */
    SystemResult getUserByName(String username);
    
    /**
     * 通过用户姓名（trueName）模糊查询用户列表
     * <br>作者： mht<br> 
     * 时间：2018年8月20日-下午3:31:31<br>
     * @param trueName
     * @return
     */
    SystemResult getUserByTrueName(String trueName);
    
    /**
     * 用户账号查重
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午11:19:02<br>
     * @param username
     * @return
     */
    SystemResult checkUsername(String username);
    /**
     * 添加或更新用户
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午8:22:49<br>
     * @param user
     * @return
     */
    SystemResult createOrUpdUser(User user);
    /**
     * 根据角色ID查询对应用户的集合
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午7:31:22<br>
     * @param roleId
     * @return
     */
    List<User> findUsersByRoleId(Integer roleId);
    /**
     * 成员列表
     * <br>作者： mht<br> 
     * 时间：2018年8月6日-上午10:35:13<br>
     * @param roleId
     * @param deptId
     * @param username
     * @return
     */
    SystemResult selectUsers(Integer pageNum, Integer pageSize, Integer roleId, Integer deptId, String username);
    
    /**
     * 根据用户id删除用户以及用户-角色对应关系
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:59:33<br>
     * @param userId
     * @return
     */
    SystemResult deleteUser(Integer userId);
    
    /**
     * 重置指定用户id的用户密码和盐值
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午6:24:32<br>
     * @param userId
     * @return
     */
    SystemResult resetPassword(Integer userId);
    
    /**
     * 更新用户密码
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午9:23:04<br>
     * @param user
     * @return
     */
    SystemResult updatePassword(User user);
    
    /**
     * 校验用户输入原密码是否正确<br>
     * 仅用于用户修改密码时校验原密码是否正确所用。不作为登录校验用
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午8:30:02<br>
     * @param password
     * @return
     */
    SystemResult checkPassword(String password);
    
    /**
     * 获取当前用户简单信息
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午9:06:22<br>
     * @return
     */
    SystemResult getCurrentUser();
    
    /**
     * 通过用户名查找用户
     * @author wxh
     * @param username
     * @return
     */
    User findByName(String username);
    
    
}
