package com.ahav.reserve.serviceimpl;

import com.ahav.reserve.mapper.RoomMapper;
import com.ahav.reserve.pojo.Result;
import com.ahav.reserve.pojo.Room;
import com.ahav.reserve.service.IRoomService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class RoomServiceImpl implements IRoomService {
    @Autowired
    private RoomMapper roomMapperImpl;
    @Autowired
    private RestTemplate restTemplate;

    //修改pub模板（查看会议室的pub模板）
    @Override
    public JSONObject selectRoomPubTemplate(int roomId) {
        //查询对应会议室对应的pub的Ip
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        Room room = roomMapperImpl.selectByPrimaryKey(roomId);
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
        }
    }

    //根据会议室ID查询出会议室姓名
    @Override
    public String findRoomName(Integer roomId) {

        return roomMapperImpl.selectRoomName(roomId);
    }

}
