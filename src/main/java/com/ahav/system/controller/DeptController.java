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

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/depts")
public class DeptController {
    @Autowired
    private DeptService deptService;
    
    @ApiOperation(value = "查询部门列表")
    @GetMapping
    public SystemResult findAllDepts() {
        return deptService.allDepts();
    }
    
    @ApiOperation(value = "更新部门设置")
    @PostMapping
    public SystemResult saveDeptSettings(@RequestBody DeptSettings depts) {
        return deptService.saveDeptSettings(depts);
    }
    
}
