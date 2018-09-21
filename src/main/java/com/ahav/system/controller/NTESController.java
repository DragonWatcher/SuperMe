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

@Api("网易接口测试：牟昊天")
@RestController
@RequestMapping("/ntes")
public class NTESController {
    @Autowired
    private NtesService ntesService;
    
    @Autowired
    private LoginService loginService;
    
    @ApiOperation(value = "部门更新接口", notes = "同步网易企业邮箱部门列表，此接口仅测试时使用，未来将该接口改造成定时更新的方式")
    @GetMapping("/units")
    public SystemResult updLocalDeptTable() {
        return ntesService.updLocalDepts();
    }
    
    @ApiOperation(value = "账号更新接口", notes = "账号同步接口，拉取网易企业中对应企业的成员列表，OA对接后此接口将废弃")
    @GetMapping("/accounts")
    public JSONObject updLocalAccount() {
        return ntesService.getNtesAccountData();
    }
    
    @ApiOperation(value = "获取ip地址", notes = "获取请求对象的外网IP地址，本机调用不会生效，可两台电脑之间访问调用")
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
