package com.ahav.system.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.SystemResult;
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
        String xIp = request.getHeader("X-Real-IP");
        String xFor = request.getHeader("X-Forwarded-For");
        String ProxyClientIP = request.getHeader("Proxy-Client-IP");
        String WLProxyClientIP = request.getHeader("WL-Proxy-Client-IP");
        String HTTP_CLIENT_IP = request.getHeader("HTTP_CLIENT_IP");
        String HTTP_X_FORWARDED_FOR = request.getHeader("HTTP_X_FORWARDED_FOR");
        String remoteAddr = request.getRemoteAddr();
        JSONObject ipList = new JSONObject();
        ipList.put("X-Real-IP", xIp);
        ipList.put("xFor", xFor);
        ipList.put("Proxy-Client-IP", ProxyClientIP);
        ipList.put("WLProxyClientIP", WLProxyClientIP);
        ipList.put("HTTP_CLIENT_IP", HTTP_CLIENT_IP);
        ipList.put("HTTP_X_FORWARDED_FOR", HTTP_X_FORWARDED_FOR);
        ipList.put("remoteAddr", remoteAddr);
        return new SystemResult(HttpStatus.OK.value(), "ip", ipList);
    }

}
