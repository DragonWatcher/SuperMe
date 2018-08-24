package com.ahav.system.service;

import javax.servlet.http.HttpServletResponse;

import com.ahav.system.entity.SystemResult;

/**
 * 登录服务
 * <br>类名：LoginService<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午2:01:02<br>
 */
public interface LoginService {
    /**
     * 系统登录
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午2:23:38<br>
     * @param username
     * @param password
     * @return
     */
    SystemResult login(String username, String password);
    
    /**
     * 当前用户登出
     * <br>作者： mht<br> 
     * 时间：2018年8月3日-下午3:51:50<br>
     * @return
     */
    SystemResult logout();
    
    /**
     * 网易邮箱单点登录
     * <br>作者： mht<br> 
     * 时间：2018年8月20日-下午1:56:58<br>
     */
    void ntesLogin(String username, HttpServletResponse response);

}
