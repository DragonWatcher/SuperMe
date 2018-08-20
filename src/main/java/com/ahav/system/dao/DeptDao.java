package com.ahav.system.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ahav.system.entity.Dept;

/**
 * 部门Dao接口
 * <br>类名：DeptDao<br>
 * 作者： mht<br>
 * 日期： 2018年8月13日-下午9:06:38<br>
 */
@Mapper
public interface DeptDao {
    
    /**
     * 查询全部部门列表
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午8:58:06<br>
     * @return
     */
    List<Dept> allDepts();
    
    /**
     * 删除部门信息
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午9:16:09<br>
     * @param deptId
     */
    void delDeptById(Integer deptId);
    
    /**
     * 新增部门信息
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午9:31:17<br>
     * @param dept
     */
    void insertDept(Dept dept);
    
    /**
     * 更新部门信息
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午9:32:06<br>
     * @param dept
     */
    void updateDept(Dept dept);
    
    /**
     * 根据部门id查找部门信息
     * <br>作者： mht<br> 
     * 时间：2018年8月16日-上午11:42:19<br>
     * @param deptId
     * @return
     */
    Dept getDeptById(Integer deptId);

}
