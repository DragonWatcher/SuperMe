package com.ahav.system.service;

import com.ahav.system.entity.Dept;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.impl.DeptServiceImpl.DeptSettings;

public interface DeptService {
    /**
     * 查询全部部门列表
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午8:58:06<br>
     * @return
     */
    SystemResult allDepts();
    
    /**
     * 更新已有部门、添加新增部门、删除废弃部门
     * <br>作者： mht<br> 
     * 时间：2018年8月9日-下午9:41:57<br>
     * @param deptsJo
     * @return
     */
    SystemResult saveDeptSettings(DeptSettings depts);
    
    /**
     * 根据部门id查找部门
     * <br>作者： mht<br> 
     * 时间：2018年8月16日-下午12:19:00<br>
     * @param deptId
     * @return
     */
    Dept getDeptById(String deptId);

}
