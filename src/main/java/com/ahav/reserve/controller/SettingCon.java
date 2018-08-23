package com.ahav.reserve.controller;

import com.ahav.reserve.pojo.RoomSettings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ahav.reserve.pojo.Result;
import com.ahav.reserve.pojo.Room;
import com.ahav.reserve.service.IRoomService;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Controller
@Api(value = "设置，会议室设置")
public class SettingCon {
    @Autowired
    private IRoomService iRoomServiceImpl;
    
    /*//添加会议室
    @RequestMapping(value = "/setting/roomSetting/insertRoom",method = RequestMethod.POST)
    @ResponseBody
    @ApiOperation(value="添加会议室", notes="添加会议室")
    @ApiImplicitParams({
        @ApiImplicitParam(paramType="query", name = "meetingRoomName", value = "会议室名称", required = true, dataType = "String"),
        @ApiImplicitParam(paramType="query", name = "meetingRoomScale", value = "会议室规模", required = true, dataType = "Integer"),
        @ApiImplicitParam(paramType="query", name = "pubIp", value = "pubIp地址", required = false, dataType = "String"),
})
    public JSONObject insertRoom(Room room){
        return iRoomServiceImpl.insertRoom(room);
    }
    
    //查询所有会议室
    @RequestMapping(value = "/setting/roomSetting/selectRoom",method = RequestMethod.GET)
    @ApiOperation(value="初始化会议室设置界面", notes="查询所有会议室")
    @ResponseBody
    public JSONObject selectRoom(){
        return iRoomServiceImpl.selectAllRoom();
    }
    
    //根据会议室id查询会议室
    @RequestMapping(value = "/setting/roomSetting/selectRoomById/{meetingRoomId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查询会议室", notes="根据会议室id查询会议室")
    public JSONObject selectRoomById(@PathVariable int meetingRoomId){
        return iRoomServiceImpl.selectRoomById(meetingRoomId);
    }
    
    //删除会议室
    @RequestMapping(value = "/setting/roomSetting/deleteRoom/{meetingRoomId}",method = RequestMethod.DELETE)
    @ResponseBody
    @ApiOperation(value="删除会议室", notes="根据会议室id删除会议室")
    public Result deleteRoom(@PathVariable int meetingRoomId){
        return iRoomServiceImpl.deleteRoom(meetingRoomId);
    }*/

    //查询所有会议室
    @RequestMapping(value = "/setting/roomSetting/selectRoom",method = RequestMethod.GET)
    @ApiOperation(value="初始化会议室设置界面", notes="查询所有会议室")
    @ResponseBody
    public JSONObject selectRoom(){
        return iRoomServiceImpl.selectAllRoom();
    }

    //根据会议室id查询会议室
    @RequestMapping(value = "/setting/roomSetting/selectRoomById/{meetingRoomId}",method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value="查询会议室", notes="根据会议室id查询会议室")
    public JSONObject selectRoomById(@PathVariable int meetingRoomId){
        return iRoomServiceImpl.selectRoomById(meetingRoomId);
    }

    //更新部门设置
    @RequestMapping(value = "/setting/roomSetting/redactRooms",method = RequestMethod.POST)
    @ApiOperation(value = "编辑会议室",notes = "删除会议室，添加会议室，修改会议室，可传参数：meetingRoomId，meetingRoomScale，meetingRoomName")
    @ResponseBody
    public JSONObject saveRoomSettings(@RequestBody RoomSettings roomSettings){
        return iRoomServiceImpl.saveRoomSettings(roomSettings);
    }
}
