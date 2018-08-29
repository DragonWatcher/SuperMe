package com.ahav.system.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.LoginService;
import com.ahav.system.service.NtesService;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("网易接口：牟昊天")
@RestController
@RequestMapping("/ntes")
public class NTESController {
    @Autowired
    private LoginService loginService;
    
    @Autowired
    private NtesService ntesService;
    
    /**
     * 网易邮箱单点登录测试接口
     * 默认登录账号是：SystemConstant.DEFAULT_ACCOUNT
     * <br>作者： mht<br> 
     * 时间：2018年8月21日-上午10:04:51<br>
     */
    @ApiOperation(value = "单点登录接口", notes = "单点登录跳转，默认登录账号：haotian.mou@ahav.com.cn")
    @GetMapping("/select")
    public SystemResult ntesLogin(HttpServletResponse response) {
//        loginService.ntesLogin(null, response);
        return ntesService.updLocalDeptTable();
    }

}
