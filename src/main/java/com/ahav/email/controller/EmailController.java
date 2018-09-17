package com.ahav.email.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahav.email.pojo.Email;
import com.ahav.email.pojo.ReceiveMail;
import com.ahav.email.service.EmailService;
import com.alibaba.fastjson.JSONObject;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api("邮件设置模块的api")
public class EmailController {
    @Autowired
    private EmailService emailService;
    @PostMapping("/emails")
    @ApiOperation(value = "新增发件箱", notes = "设置发件箱信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = JSONObject.class),
            @ApiResponse(code = 400, message = "Bad Request", response = JSONObject.class)
    })
    public JSONObject saveEmails( @RequestBody Email email) {
    	System.out.println("szq邮箱接口");
        JSONObject jo = emailService.insertEmail(email);
        return jo;
    }
    @ApiOperation(value = "查询发件箱", notes = "查询邮箱信息")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = JSONObject.class),
            @ApiResponse(code = 400, message = "Bad Request", response = JSONObject.class)
    })
    @GetMapping ("/emails")
    public JSONObject getEmails() {
    	System.out.println("szq邮箱接口");
        JSONObject jo = emailService.getEmail();
        return jo;
    }
    @PutMapping("/emails")
    @ApiOperation(value = "修改发件箱", notes = "修改邮箱设置")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = JSONObject.class),
            @ApiResponse(code = 400, message = "Bad Request", response = JSONObject.class)
    })
    @ApiImplicitParam(dataType = "Integer", name = "serverId", value = "服务器编号", required = true)
    public JSONObject editEmails( @RequestBody Email email) {
    	System.out.println("szq邮箱接口");
        JSONObject jo = emailService.editEmail(email);
        return jo;
    }
    @PostMapping("/sendemails")
    @ApiOperation(value = "发送邮件", notes = "发送邮件功能")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK", response = JSONObject.class),
            @ApiResponse(code = 400, message = "Bad Request", response = JSONObject.class)
    })
    public JSONObject sendMail(ReceiveMail receiveMail){
    	System.out.println("szq邮箱接口");
        JSONObject jo = emailService.sendEmail(receiveMail);
        return jo;
    }


}
