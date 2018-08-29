package com.ahav.reserve.controller;

import com.ahav.reserve.pojo.MeetingDetails;
import com.ahav.reserve.service.IMeetingDetailsService;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.service.LoginService;
import com.ahav.system.service.UserService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@Api(value = "预约|历史查询模块")
public class historySelectCon {

    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;

    /*@Autowired
    private UserService loginService;*/

    @RequestMapping(value = "/reserve/history/selectHistory",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="历史查询", notes="按照查询条件找到相应的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "deRoomName", value = "会议室名称", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStatus", value = "是否释放", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStart", value = "查询开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "deMeetingOver", value = "查询结束时间", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "deDepartmentReservePersonId", value = "部门预定人id", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveDepartmentId", value = "预定部门Id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReserveId", value = "预定人Id", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deGradeId", value = "会议级别ID", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReservePhone", value = "预定人电话", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "pageNum", value = "当前页", required = false, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "pageSize", value = "每页展示几条内容", required = false, dataType = "Integer"),
    })
    public JSONObject selectHistory(MeetingDetails meetingDetails, @RequestParam(defaultValue = "1") Integer pageNum,@RequestParam(defaultValue = "10")Integer pageSize)  {
        return meetingDetailsServiceImpl.selectHistory(meetingDetails,pageNum,pageSize);
    }


    /*@RequestMapping(value = "/test/userLogin",method = RequestMethod.GET)
    @ResponseBody
    public SystemResult userLogin(){
       return loginService.getUserByName("mht");
    }*/

}
