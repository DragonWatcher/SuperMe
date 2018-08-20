package com.ahav.reserve.controller;

import com.ahav.reserve.pojo.MeetingDetails;
import com.ahav.reserve.pojo.PubTemplate;
import com.ahav.reserve.pojo.Result;
import com.ahav.reserve.pojo.Room;
import com.ahav.reserve.service.IMeetingDetailsService;
import com.ahav.reserve.service.IRoomService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class PubTemplateCon {
    @Autowired
    private IMeetingDetailsService meetingDetailsServiceImpl;
    @Autowired
    private IRoomService roomServiceImpl;
    @Autowired
    private RestTemplate restTemplate;

    //修改pub模板（查看会议室的pub模板）
    @RequestMapping(value = "/reserve/manage/select/room/pubTemplate/{roomId}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectRoomPubTemplate(@PathVariable int roomId) {
        /*//查询对应会议室对应的pub的Ip
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        Room room = roomServiceImpl.findRoom(roomId);
        String pubIp = room.getPubIp();
        //调用api接口
        String body = restTemplate.getForEntity("http://"+pubIp+"/ajax/presetmode/list", String.class).getBody();
        if(body != null && body != ""){
            result.setStatus(200);
            JSONArray pubTemplateArray = JSONArray.parseArray(body);//将json数组格式的字符串，转为json数组
            jsonObject.put("pubTemplateArray",pubTemplateArray);//将json数组封装到json对象中
            jsonObject.put("result",result);//将json数组封装到json对象中
            return jsonObject;
        }else {
            result.setStatus(400);
            jsonObject.put("result",result);//将json数组封装到json对象中
            return jsonObject;
        }*/
        return roomServiceImpl.selectRoomPubTemplate(roomId);
    }


    //查看会议当前模板
    @RequestMapping(value = "/select/meeting/pubTemplate/{deDetailsId}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectMeetingPubTemplate(@PathVariable int deDetailsId){
/*        JSONObject jsonObject = new JSONObject();
        Result result = new Result();
        MeetingDetails meetingDetails = meetingDetailsServiceImpl.findMeetingDetails(deDetailsId);
        if(meetingDetails != null){
            String dePubTemplate = meetingDetails.getDePubTemplate();
            result.setStatus(200);
            jsonObject.put("result",result);
            jsonObject.put("dePubTemplate",dePubTemplate);
            return jsonObject;
        }else{
            result.setStatus(400);
            return jsonObject;
        }*/
        return meetingDetailsServiceImpl.findMeetingPubTemplate(deDetailsId);
    }

/*    //继续并添加Pub模板
    @RequestMapping(value = "/reserve/add/select/room/pubTemplate/{deDetailsId}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject selectRoomPubTemplate(@PathVariable int deDetailsId){
        //调用添加会议室的方法
        meetingDetailsServiceImpl.addMeetingDetails()
    }*/

    //保存pub模板，根据选择的模板更新已选择的会议中的pub模板
    @RequestMapping(value = "/reserve/",method = RequestMethod.PUT)
    @ResponseBody
    public Result updatePubTemplate(PubTemplate pubTemplate,int deDetailsId){
       return meetingDetailsServiceImpl.saveTemplate(pubTemplate,deDetailsId);
    }

}
