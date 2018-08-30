package com.ahav.system.service;

import com.ahav.system.entity.SystemResult;

/**
 * 网易邮箱接口服务类
 * <br>类名：NtesService<br>
 * 作者： mht<br>
 * 日期： 2018年8月28日-上午9:51:07<br>
 */
public interface NtesService {
    
    /**
     * 更新本地部门数据
     * <br>作者： mht<br> 
     * 时间：2018年8月29日-下午2:51:22<br>
     * @return
     */
    SystemResult updLocalDeptTable();

    /**
     * 更新本地账号数据
     * <br>作者： mht<br> 
     * 时间：2018年8月30日-下午4:43:31<br>
     * @return
     */
    SystemResult updLocalAccount();
}
