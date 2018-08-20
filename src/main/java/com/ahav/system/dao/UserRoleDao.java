package com.ahav.system.dao;

import org.apache.ibatis.annotations.Mapper;

import com.ahav.system.entity.User;
import com.ahav.system.entity.UserRoleKey;

@Mapper
public interface UserRoleDao {
    
    /**
     * 添加成员角色对应关系
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:28:07<br>
     * @param user
     */
    void insertUserRole(User user);

    /**
     * 更新成员角色对应关系
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:42:29<br>
     * @param user
     */
    void updateUserRole(User user);
    
    /**
     * 根据用户id删除用户-角色对应关系
     * <br>作者： mht<br> 
     * 时间：2018年8月11日-下午5:58:45<br>
     * @param userId
     */
    void deleteUserRoleByUserId(Integer userId);
    
    int findRoleId(Integer userId);
    
    int deleteByPrimaryKey(UserRoleKey key);

    int insert(UserRoleKey record);

    int insertSelective(UserRoleKey record);

    void deleteUserRole(Integer roleId);

}
