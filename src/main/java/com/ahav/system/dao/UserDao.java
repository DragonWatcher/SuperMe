package com.ahav.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ahav.system.entity.SimpleUser;
import com.ahav.system.entity.User;

/**
 * 用户持久层 <br>
 * 类名：UserDao<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午10:43:15<br>
 */
@Mapper
public interface UserDao {

    /**
     * @author wxh
     * @param username
     * @return
     */
    User findByName(String username);

    /**
     * 根据用户id查找对应用户信息 <br>
     * 作者： mht<br>
     * 时间：2018年8月11日-下午4:22:52<br>
     * 
     * @param userId
     * @return
     */
    SimpleUser selectUserById(Integer userId);

    /**
     * 根据真实姓名模糊查询 <br>
     * 作者： mht<br>
     * 时间：2018年8月20日-下午3:33:45<br>
     * 
     * @param trueName
     * @return
     */
    List<SimpleUser> selectUserByTrueName(String trueName);

    /**
     * 通过用户名查找账号信息 <br>
     * 作者： mht<br>
     * 时间：2018年8月3日-上午10:44:19<br>
     * 
     * @param username
     * @return
     */
    User selectUserByName(String username);

    /**
     * 用户账号查重 <br>
     * 作者： mht<br>
     * 时间：2018年8月3日-下午10:49:36<br>
     * 
     * @param username
     * @return
     */
    String selectUsername(String username);

    /**
     * 成员列表 <br>
     * 作者： mht<br>
     * 时间：2018年8月6日-上午10:35:13<br>
     * 
     * @param roleId
     * @param deptId
     * @param username
     * @return
     */
    List<SimpleUser> selectUsers(@Param("roleId") Integer roleId, @Param("deptId") Integer deptId,
            @Param("username") String username);

    /**
     * 根据角色ID查询对应用户的集合 <br>
     * 作者： mht<br>
     * 时间：2018年8月9日-下午7:31:22<br>
     * 
     * @param roleId
     * @return
     */
    List<User> selectUsersByRoleId(Integer roleId);

    /**
     * 根据deptId 和 roleId 查询用户 <br>
     * 作者： mht<br>
     * 时间：2018年8月22日-上午11:34:42<br>
     * 
     * @param deptId
     * @param roleId
     * @return
     */
    List<User> selectUserByDeptIdAndRoleId(@Param("deptId") String deptId, @Param("roleId") Integer roleId);
    
    /**
     * 添加新成员 <br>
     * 作者： mht<br>
     * 时间：2018年8月3日-下午10:49:01<br>
     * 
     * @param user
     */
    boolean insertUser(User user);

    /**
     * 根据用户id删除用户 <br>
     * 作者： mht<br>
     * 时间：2018年8月11日-下午5:56:40<br>
     * 
     * @param userId
     * @return
     */
    boolean deleteUser(Integer userId);

    /**
     * 更新成员 <br>
     * 作者： mht<br>
     * 时间：2018年8月11日-下午5:44:28<br>
     * 
     * @param user
     * @return
     */
    boolean updateUser(User user);

    /**
     * 重置用户密码 <br>
     * 作者： mht<br>
     * 时间：2018年8月11日-下午6:23:55<br>
     * 
     * @param user
     */
    boolean updatePassword(User user);

    /**
     * 设置用户界面颜色 <br>
     * 作者： mht<br>
     * 时间：2018年8月24日-下午3:11:11<br>
     * 
     * @param user
     */
    boolean updateUserColor(SimpleUser user);
    
    /**
     * 设置用户头像保存路径
     * <br>作者： mht<br> 
     * 时间：2018年8月25日-上午8:35:02<br>
     * @param user
     * @return
     */
    boolean updateUserProfilePath(SimpleUser user);
}
