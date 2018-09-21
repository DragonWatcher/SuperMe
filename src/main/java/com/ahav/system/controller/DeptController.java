package com.ahav.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.DeptService;
import com.ahav.system.service.impl.DeptServiceImpl.DeptSettings;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api("部门设置：牟昊天")
@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;

    @ApiOperation(value = "组织架构查看", notes = "这个接口非常关键，是组织架构查看的重要接口，不需要传入任何参数")
    @GetMapping(value = "/users")
    public SystemResult viewDeptsAndUsers() {
        return deptService.viewDeptsAndUsers();
    }
    
    @ApiOperation(value = "查询部门列表", notes = "查找部门列表，与网易邮箱和OA系统对接组织架构后，已废弃该功能")
    @GetMapping
    @Deprecated
    public SystemResult findAllDepts() {
        return deptService.allDepts();
    }

    @ApiOperation(value = "更新部门设置", notes = "设置部门，与网易邮箱和OA系统对接组织架构后，已废弃该功能")
    @PostMapping
    @Deprecated
    public SystemResult saveDeptSettings(@RequestBody DeptSettings depts) {
        return deptService.saveDeptSettings(depts);
    }

}
