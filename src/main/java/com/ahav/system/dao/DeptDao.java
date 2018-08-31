package com.ahav.system.dao;

import java.util.List;
import java.util.Set;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.ahav.system.entity.Dept;
import com.ahav.system.enums.NtesDataVer;

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
     * 根据部门id查找对应的部门信息
     * <br>作者： mht<br> 
     * 时间：2018年8月30日-下午1:03:03<br>
     * @param deptId
     * @return
     */
    Dept selectDeptById(String deptId);
    
    /**
     * 查询部门id列表
     * <br>作者： mht<br> 
     * 时间：2018年8月30日-下午1:12:38<br>
     * @return
     */
    List<String> selectDeptIdList();
    
    /**
     * 删除部门信息
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午9:16:09<br>
     * @param deptId
     */
    void delDeptById(String deptId);
    
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
    Dept getDeptById(String deptId);
    
    /**
     * 查询网易数据版本
     * <br>作者： mht<br> 
     * 时间：2018年8月29日-下午3:19:16<br>
     * @param dataName
     * @return
     */
    Long selectUnitDataVer(NtesDataVer dataName);

    /**
     * 更新网易邮箱数据版本
     * <br>作者： mht<br> 
     * 时间：2018年8月30日-上午10:47:54<br>
     * @param dataName
     * @param dataVer
     */
    void updateDataVer(@Param("dataName") NtesDataVer dataName, @Param("dataVer") Long dataVer);
    
    /**
     * 根据部门id列表批量删除部门
     * <br>作者： mht<br> 
     * 时间：2018年8月30日-下午1:29:56<br>
     * @param deptIdList
     */
    void delDeptsBatch(@Param("deptIdList") Set<String> deptIdList);
}
