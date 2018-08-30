package com.ahav.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.NtesService;

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
        return ntesService.updLocalDeptTable();
    }
    
    @ApiOperation(value = "账号更新接口")
    @GetMapping("/accounts")
    public SystemResult updLocalAccount() {
        return ntesService.updLocalAccount();
    }

}
