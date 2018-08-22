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
    
    //添加会议室
    @Override
    public JSONObject insertRoom(Room room) {
        JSONObject jsonObject = new JSONObject();
        Result result = new Result();
        //通过会议室姓名查询会议室
        Room room1 = roomMapperImpl.selectRoomByName(room.getMeetingRoomName());
        System.err.println(room1);
        if(room1 == null){
            //会议室没有重名
            int insert = roomMapperImpl.insert(room);
            if(insert > 0){
                result.setStatus(200);
                jsonObject.put("result", result);
            }else{
                result.setStatus(400);
                jsonObject.put("result", result);
            }
            return jsonObject;
        }else{
            //会议室重名
            result.setStatus(400);
            jsonObject.put("result", result);
            return jsonObject;
        }
        
    }
    
    //查询所有会议室
    @Override
    public JSONObject selectAllRoom() {
        JSONObject jsonObject = new JSONObject();
        Result result = new Result();
        List<Room> allRoom = roomMapperImpl.selectRoomAll();
        result.setStatus(200);
        jsonObject.put("allRoom", allRoom);
        return jsonObject;
    }
    
    //根据会议室id删除会议室
    @Override
    public Result deleteRoom(int meetingRoomId) {
        Result result = new Result();
        int delete = roomMapperImpl.deleteByPrimaryKey(meetingRoomId);
        if(delete > 0){
            result.setStatus(200);
        }else{
            result.setStatus(400);
        }
        return result;
    }
    
    //根据会议室id查询会议室
    @Override
    public JSONObject selectRoomById(int meetingRoomId) {
        JSONObject jsonObject = new JSONObject();
        Result result = new Result();
        Room room = roomMapperImpl.selectByPrimaryKey(meetingRoomId);
        if(room != null){
            result.setStatus(200);
            jsonObject.put("room", room);
        }else{
            result.setStatus(400);
        }
        return jsonObject;
    }


}
