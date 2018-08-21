package com.ahav.system.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.service.LoginService;
import com.ahav.system.util.SystemConstant;

@RestController
@RequestMapping("/ntes")
public class NTESController {
    @Autowired
    private LoginService loginService;
    
    /**
     * 网易邮箱单点登录测试接口
     * 默认登录账号是：SystemConstant.DEFAULT_ACCOUNT
     * <br>作者： mht<br> 
     * 时间：2018年8月21日-上午10:04:51<br>
     */
    @GetMapping("/login")
    public void ntesLogin(HttpServletResponse response) {
        loginService.ntesLogin(null, response);
    }

}
