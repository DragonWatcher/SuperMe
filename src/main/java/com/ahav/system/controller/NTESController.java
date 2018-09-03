package com.ahav.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.LoginService;
import com.ahav.system.service.NtesService;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("网易接口测试")
@RestController
@RequestMapping("/ntes")
public class NTESController {
    @Autowired
    private NtesService ntesService;
    
    @Autowired
    private LoginService loginService;
    
    @ApiOperation(value = "部门更新接口")
    @GetMapping("/units")
    public SystemResult updLocalDeptTable() {
        return ntesService.updLocalDepts();
    }
    
    @ApiOperation(value = "账号更新接口")
    @GetMapping("/accounts")
    public SystemResult updLocalAccount() {
        return ntesService.updLocalAccounts();
    }
    
    @ApiOperation(value = "获取ip地址")
    @GetMapping("/ip")
    public SystemResult reviewIp(HttpServletRequest request) {
        return new SystemResult(HttpStatus.OK.value(), "remoteAddr", request.getRemoteAddr());
    }
    
    @ApiOperation(value = "网易邮箱登录")
    @GetMapping("/login")
    public void ntesLogin(HttpServletResponse response) {
        loginService.ntesLogin(null, response);
    }

}
