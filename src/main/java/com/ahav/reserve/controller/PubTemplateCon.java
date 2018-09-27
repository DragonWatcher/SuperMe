package com.ahav.reserve.controller;
import com.ahav.reserve.pojo.MeetingDetails;
import com.ahav.reserve.pojo.PubTemplate;
import com.ahav.reserve.pojo.Result;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Map;

@Controller
@Api(value = "预约|Pub模板")
public class PubTemplateCon {
    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;
    @Autowired
    private IRoomService roomServiceImpl;
    @Autowired
    private RestTemplate restTemplate;

    //修改pub模板（查看会议室的pub模板）
    @RequestMapping(value = "/reserve/manage/select/room/pubTemplate/{id}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="修改pub模板", notes="修改Pub模板的第一步，需要会议详情的id")
    @ApiImplicitParam(paramType="path", name = "id", value = "会议详情ID", required = true, dataType = "Integer")
    public JSONObject selectRoomPubTemplate(@PathVariable int id) {
        return roomServiceImpl.selectRoomPubTemplate(id);
    }

    //查看会议当前模板
    @RequestMapping(value = "/select/meeting/seletePubTemplate/{id}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查看当前pub预设模板", notes="查看当前会议预设的pub模板，需要会议详情的id")
    @ApiImplicitParam(paramType="path", name = "id", value = "会议详情ID", required = true, dataType = "Integer")
    public JSONObject selectMeetingPubTemplate(@PathVariable int id){
        return meetingDetailsServiceImpl.findMeetingPubTemplate(id);
    }


    //保存pub模板，根据选择的模板更新已选择的会议中的pub模板
    @RequestMapping(value = "/reserve/manage/updatePubTemplate",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value="保存pub模板", notes="修改模板第二步，根据选择的模板更新已选择的会议中的pub模板")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "id", value = "会议详情ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deGrade", value = "会议级别", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deGradeId", value = "会议级别ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deRoomId", value = "会议室Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStartStr", value = "会议开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingOverStr", value = "会议结束时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingName", value = "会议详情名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveDepartmentId", value = "预定部门Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReserve", value = "预定人", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveId", value = "预定人id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReservePhone", value = "预定人电话", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveNumber", value = "预定人数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMain", value = "主要人员", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "text", value = "会议备注", required = true, dataType = "String"),
    })
    public Map updatePubTemplate(MeetingDetails meetingDetails, @RequestBody PubTemplate pubTemplate, String deMeetingStartStr,String deMeetingOverStr){//接受一个json对象，要求前台传递的json对象中的每一个key与我们接受类的属性名一一对应，json对象的名与类名一致
        meetingDetails.setDeMeetingStart(meetingUtils.parse(deMeetingStartStr));
        meetingDetails.setDeMeetingOver(meetingUtils.parse(deMeetingOverStr));
        //目前从swagger中传json对象pubTemplate接收不到
       return meetingDetailsServiceImpl.saveTemplate(meetingDetails,pubTemplate);
    }

    //删除pub模板（实际不是删除Pub模板，而是将预设模板改为无模板的那个模板）
    @RequestMapping(value = "/reserve/manage/updatePubTemplate/{id}",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value="删除pub模板", notes="实际不是删除Pub模板，而是将预设模板改为无模板的那个模板")
    @ApiImplicitParam(paramType="path", name = "id", value = "会议详情id", required = true, dataType = "Integer")
    public Map updatePubTemplate(@PathVariable Integer id){
        String pubTemplate = "{\"Content\":\"\",\"LayoutId\":-1,\"Thumb\":\"https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=69fb9c56cb95d143da76e3254bcbe53f/d1a20cf431adcbef5d550e53afaf2edda3cc9f05.jpg\",\"Id\":-1,\"Name\":\"nullTemplate\"}";
        return meetingDetailsServiceImpl.deletePubTemplate(id,pubTemplate);
    }


    //继续并添加Pub模板---通过会议室id查询该会议的pub模板
    @RequestMapping(value = "/reserve/add/addPubTemplate/{roomId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="继续并添加Pub模板第一步", notes="通过会议室id查询该会议的pub模板")
    @ApiImplicitParam(paramType="path", name = "roomId", value = "会议室id", required = true, dataType = "Integer")
    public JSONObject addRoomPubTemplate(@PathVariable int roomId){
        return roomServiceImpl.byRoomIdSelectRoomPubTemplate(roomId);
    }
    
    
    //继续并添加Pub模板---完成
    @RequestMapping(value = "/reserve/add/addPubTemplate",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="继续并添加Pub模板---完成", notes=" ")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "deGrade", value = "会议级别", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deGradeId", value = "会议级别ID", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deRoomId", value = "会议室Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMeetingStartStr", value = "会议开始时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingOverStr", value = "会议结束时间", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deMeetingName", value = "会议详情名称", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveDepartmentId", value = "预定部门Id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReserve", value = "预定人", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveId", value = "预定人id", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deReservePhone", value = "预定人电话", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "deReserveNumber", value = "预定人数", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType="query", name = "deMain", value = "主要人员", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "text", value = "会议备注", required = true, dataType = "String"),
    })
    public JSONObject addRoomPubTemplate2(MeetingDetails meetingDetails,@RequestBody PubTemplate pubTemplate,String deMeetingStartStr,String deMeetingOverStr){
        meetingDetails.setDeMeetingStart(meetingUtils.parse(deMeetingStartStr));
        meetingDetails.setDeMeetingOver(meetingUtils.parse(deMeetingOverStr));
        //目前从swagger中传json对象pubTemplate接收不到
        return meetingDetailsServiceImpl.addMeetingDetails(meetingDetails,pubTemplate);
    }


}
