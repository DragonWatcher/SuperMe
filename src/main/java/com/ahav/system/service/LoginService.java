package com.ahav.system.service;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.util.SystemConstant;
import com.netease.domainmail.RSATool;

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
    SystemResult login(String username, String password, HttpServletResponse response);
    
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
    default void ntesLogin(String username, HttpServletResponse response) {
        // 测试账号,牟昊天的企业邮箱
        String account_name = SystemConstant.DEFAULT_ACCOUNT;
        
        // 当前时间戳
        long currTime = System.currentTimeMillis();
        
        // 安恒网易企业邮箱域名，为啥域名登录前后会变化？mail.ahav.com.cn/owa/
        String domain = SystemConstant.AHAV_DOMAIN;
        
        // 私钥
        String priKey = SystemConstant.PRI_KEY;
        
        // 要加密的信息
        String src = account_name + domain + currTime;
        
        RSATool rsa = new RSATool();
        //加密串 (摘要)
        String enc = rsa.generateSHA1withRSASigature(src, priKey);
        
        //提交登录的url,后台加上必须的参数,为了安全，可使用https提交
        String url = "https://entryhz.qiye.163.com/domain/oa/Entry?domain=" + domain + "&account_name=" + account_name + "&time=" + currTime + "&enc=" + enc/* + "& language =" + language*/;
        
        //登录,也可以采用form表单post提交的方式。
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
