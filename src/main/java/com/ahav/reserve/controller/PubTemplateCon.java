package com.ahav.reserve.controller;
import com.ahav.reserve.pojo.PubTemplate;
import com.ahav.reserve.pojo.Result;
import com.ahav.reserve.service.IMeetingDetailsService;
import com.ahav.reserve.service.IRoomService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

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
    @RequestMapping(value = "/reserve/manage/select/room/pubTemplate/{deDetailsId}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="修改pub模板", notes="修改Pub模板的第一步，需要会议详情的id")
    @ApiImplicitParam(paramType="path", name = "deDetailsId", value = "会议详情ID", required = true, dataType = "Integer")
    public JSONObject selectRoomPubTemplate(@PathVariable int deDetailsId) {
        return roomServiceImpl.selectRoomPubTemplate(deDetailsId);
    }

    //查看会议当前模板
    @RequestMapping(value = "/select/meeting/seletePubTemplate/{deDetailsId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查看当前pub预设模板", notes="查看当前会议预设的pub模板，需要会议详情的id")
    @ApiImplicitParam(paramType="path", name = "deDetailsId", value = "会议详情ID", required = true, dataType = "Integer")
    public JSONObject selectMeetingPubTemplate(@PathVariable int deDetailsId){
        return meetingDetailsServiceImpl.findMeetingPubTemplate(deDetailsId);
    }


    //保存pub模板，根据选择的模板更新已选择的会议中的pub模板
    @RequestMapping(value = "/reserve/manage/updatePubTemplate",method = RequestMethod.PUT)
    @ResponseBody
    @ApiOperation(value="查看当前pub预设模板", notes="查看当前会议预设的pub模板，需要会议详情的id")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "deDetailsId", value = "会议详情id", required = true, dataType = "Integer"),
    })
    public Result updatePubTemplate(int deDetailsId,@RequestBody PubTemplate pubTemplate){//接受一个json对象，要求前台传递的json对象中的每一个key与我们接受类的属性名一一对应，json对象的名与类名一致
       return meetingDetailsServiceImpl.saveTemplate(deDetailsId,pubTemplate);
    }

    //删除pub模板（实际不是删除Pub模板，而是将预设模板改为无模板的那个模板）
    @RequestMapping(value = "/reserve/manage/updatePubTemplate/{deDetailsId}",method = RequestMethod.PUT)
    @ResponseBody
    public Map updatePubTemplate(int deDetailsId){
        String pubTemplate = "{\"Content\":\"\",\"LayoutId\":-1,\"Thumb\":\"https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=69fb9c56cb95d143da76e3254bcbe53f/d1a20cf431adcbef5d550e53afaf2edda3cc9f05.jpg\",\"Id\":-1,\"Name\":\"nullTemplate\"}";
        return meetingDetailsServiceImpl.deletePubTemplate(deDetailsId,pubTemplate);
    }


    /*    //继续并添加Pub模板
    @RequestMapping(value = "/reserve/add/select/room/pubTemplate/{deDetailsId}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectRoomPubTemplate(@PathVariable int deDetailsId){
        //调用添加会议室的方法
        meetingDetailsServiceImpl.addMeetingDetails()
    }*/

}
