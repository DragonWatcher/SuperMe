package com.ahav.reserve.controller;

import com.ahav.reserve.pojo.*;
import com.ahav.reserve.service.IMeetingDetailsService;
import com.ahav.reserve.service.IRoomService;
import com.ahav.reserve.utils.meetingUtils;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Api(value = "预约|预约管理，添加预约模块")
public class MeetingDetailsCon {
    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;
    @Autowired
    private IRoomService roomServiceImpl;

    //查询所有会议(初始化预约管理页面/添加预约界面)
    @RequestMapping(value = {"/reserve/manage/initReserveManage","/initAddReserve"},method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="初始化预约管理/添加预约界面", notes="初始化预约管理页面/添加预约界面不需要任何参数")
    @ApiImplicitParam(value = "用户编号")
    public Map findMeetingDetailsAll(){
        return meetingDetailsServiceImpl.findMeetingDetailsAll();
    }



    //按条件查询会议详情列表
    @RequestMapping(value = "/reserve/manage/selectMeetingDetailsList",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查询会议详情列表", notes="根据条件预定人Id,会议详情的名称，会议所在当天的日期,查询出满足条件的会议详情，预定人姓名测试数据：牟昊天丶张建国丶无花果，会议名称测试数据：年度大会丶年度小会丶冬季会议丶内部会议丶外部会议丶年度小会2丶年度小会3丶年度小会4丶年度大会2")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "deReserve", value = "预订人姓名", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingName", value = "会议详情的名称", required = false, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStart", value = "会议所在当天的日期", required = true, dataType = "Date")
    })
    public Map selectMeetingDetails(MeetingDetails meetingDetails){
        return meetingDetailsServiceImpl.findMeetingDetails(meetingDetails);
    }


    //修改会议
    @RequestMapping(value = "/reserve/manage/selectMeetingDetails",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="修改会议", notes="修改会议的第一步")
    public JSONObject updateMeetingDetails (){
        JSONObject jsonObject = new JSONObject();
        return meetingDetailsServiceImpl.alterMeetingDetails();
    }

    //保存修改会议室详情
    @RequestMapping (value = "/reserve/manage/updateMeetingDetails",method = RequestMethod.PUT)
    //@PutMapping("MeetingDetails")
    @ResponseBody
    @ApiOperation(value="保存修改会议", notes="修改会议的第二步")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "deDetailsId", value = "会议详情ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deGrade", value = "会议级别", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deGradeId", value = "会议级别ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deRoomId", value = "会议室Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStart", value = "会议开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "deMeetingOver", value = "会议结束时间", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "deMeetingName", value = "会议详情名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveDepartmentId", value = "预定部门Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReserve", value = "预定人", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveId", value = "预定人id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReservePhone", value = "预定人电话", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveNumber", value = "预定人数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMain", value = "主要人员", required = true, dataType = "String"),
    })
    public Map updateMeetingDetails(MeetingDetails meetingDetails){
           System.out.println(meetingDetails.getDeMeetingName());
        return meetingDetailsServiceImpl.alterMeetingDetails(meetingDetails);
    }

    //删除会议详情
    @RequestMapping(value = "/reserve/manage/deleteMeetingDetails/{deDetailsId}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value="删除预约", notes="删除预约，需要参数会议详情的id")
    @ApiImplicitParam(paramType="path", name = "deDetailsId", value = "会议详情ID", required = true, dataType = "Integer")
    public Result deleteMeetingDetails(@PathVariable int deDetailsId){

        return meetingDetailsServiceImpl.deleteMeetingDetails(deDetailsId);
    }

    //根据会议详情id查询详情
    @RequestMapping(value = "MeetingDetails/{deDetailsId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查看预约", notes="查看预约，需要参数会议详情的id")
    @ApiImplicitParam(paramType="path", name = "deDetailsId", value = "会议详情ID", required = true, dataType = "Integer")
    public Map selectMeetingDetails(@PathVariable int deDetailsId){
        
        return meetingDetailsServiceImpl.findMeetingDetails(deDetailsId);
    }
    //添加会议详情
    @RequestMapping(value = "/reserve/add/selectMeetingDetails",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="添加会议详情", notes="添加会议详情第一步")
    public JSONObject insertMeetingDetails(){
        return meetingDetailsServiceImpl.addMeetingDetails();
    }

    //保存添加会议详情
    @RequestMapping(value = "/reserve/add/insertMeetingDetails",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="保存添加会议", notes="添加会议的第二步")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "deGrade", value = "会议级别", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deGradeId", value = "会议级别ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deRoomId", value = "会议室Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStart", value = "会议开始时间", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "deMeetingOver", value = "会议结束时间", required = true, dataType = "Date"),
            @ApiImplicitParam(paramType="query", name = "deMeetingName", value = "会议详情名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveDepartmentId", value = "预定部门Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReserve", value = "预定人", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveId", value = "预定人id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReservePhone", value = "预定人电话", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveNumber", value = "预定人数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMain", value = "主要人员", required = true, dataType = "String"),
    })
    public Result insertMeetingDetails(MeetingDetails meetingDetails){
        return meetingDetailsServiceImpl.addMeetingDetails(meetingDetails);
    }



}
