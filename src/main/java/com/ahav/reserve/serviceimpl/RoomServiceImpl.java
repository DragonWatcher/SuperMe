package com.ahav.reserve.serviceimpl;

import com.ahav.reserve.mapper.RoomMapper;
import com.ahav.reserve.pojo.Result;
import com.ahav.reserve.pojo.Room;
import com.ahav.reserve.pojo.RoomSettings;
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

    /*//修改pub模板（查看会议室的pub模板）
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
    }*/

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

    /*//根据会议室id删除会议室
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
    }*/

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

    //保存会议室设置
    @Override
    public JSONObject saveRoomSettings(RoomSettings roomSettings) {
        String addMessage = "";//添加操作提示信息
        String updateMessage = "";//修改操作提示信息
        Boolean flag = false;
        JSONObject jsonObject = new JSONObject();
        List<Room> deleteRoomList = roomSettings.getDeleteRoomList();
        Result result = new Result();

        List<Room> roomList = roomSettings.getRoomList();
        for (Room room : roomList){
            //根据会议室id是否为空，如果为空则执行添加操作，不为空执行更新操作
            Integer meetingRoomId = room.getMeetingRoomId();
            if(meetingRoomId != null){
                //更新会议室
                Room room1 = roomMapperImpl.selectByPrimaryKey(meetingRoomId);
                if(room1.getMeetingRoomName().equals(room.getMeetingRoomName()) ){
                    if(room1.getMeetingRoomScale().equals(room.getMeetingRoomScale())){
        /*                //TODO:如果增加了pubIP这里在进行判断
                        if((room1.getPubIp()!=null && !"".equals(room1.getPubIp())) && (room.getPubIp()!=null && !"".equals(room.getPubIp())) ){
                            //原有会议室和修改会议室的pubIP都不为空
                            if(room1.getPubIp() == room.getPubIp()){
                                //修改的信息与原信息一致，所以不需要修改
                                flag = true;
                            }else {
                                int update = roomMapperImpl.updateByPrimaryKeySelective(room);
                                flag = true;
                            }
                        }*/
                        //修改的信息与原信息一致，所以不需要修改
                        flag = true;
                    }else{
                        int update = roomMapperImpl.updateByPrimaryKeySelective(room);
                        if(update > 0){
                            flag = true;
                        }else {
                            flag = false;
                            updateMessage +=room.getMeetingRoomName()+",";
                        }
                    }
                }else {
                    //修改的会议室名称与原会议室名称不一致，所以要修改
                    Room room2 = roomMapperImpl.selectRoomByName(room.getMeetingRoomName());
                    if(room2 != null){
                        //修改会议室名称跟已有会议名称冲突
                        flag = false;
                        updateMessage +=room.getMeetingRoomName()+",";
                    }else {
                        //修改会议室名称跟已有会议名称没有冲突，所以进行修改
                        int update = roomMapperImpl.updateByPrimaryKeySelective(room);
                        if(update > 0){
                            flag = true;
                        }else {
                            flag = false;
                            updateMessage +=room.getMeetingRoomName()+","; //将修改失败的会议室名称记录下来
                        }
                    }
                }
            }else {
                //添加会议室
                Room room1 = roomMapperImpl.selectRoomByName(room.getMeetingRoomName());//通过会议室姓名查询会议室
                if(room1 == null){
                    //会议室没有重名
                    int insert = roomMapperImpl.insertSelective(room);
                    if(insert > 0){
                        //添加成功
                        flag = true;
                    }
                }else {
                    //会议室重名，添加失败
                    flag = false;
                    addMessage+=room.getMeetingRoomName()+","; //将添加失败的会议室名称记录下来
                }
            }

        }

        for(Room deleteRoom : deleteRoomList){
            //根据id是否为空判断是否删除会议室
            Integer meetingRoomId = deleteRoom.getMeetingRoomId();
            if(meetingRoomId != null){
                roomMapperImpl.deleteByPrimaryKey(meetingRoomId);
            }
        }

        if(flag){
            //添加，修改，删除全部成功
            result.setStatus(200);
            List<Room> allRoom = roomMapperImpl.selectRoomAll();
            jsonObject.put("result",result);
            jsonObject.put("allRoom",allRoom);
            return jsonObject;
        }else{
            result.setStatus(400);
            if((addMessage != null &&!"".equals(addMessage)) &&  ("".equals(updateMessage) || updateMessage == null)){
                result.setMessage("添加:"+addMessage+"失败可能是因为会议室重名，其他则添加成功");
            }
            if((addMessage != null &&!"".equals(addMessage)) && (updateMessage != null &&!"".equals(updateMessage))){
                result.setMessage("添加:"+addMessage+"失败可能是因为会议室重名问题，其他则添加成功。修改："+updateMessage+"失败可能是因为修改会议室重名问题，其他则修改成功。");
            }
            if((updateMessage != null &&!"".equals(updateMessage)) && ("".equals(addMessage) || addMessage == null)){
                result.setMessage("修改:"+updateMessage+"失败可能是因为会议室重名，其他则修改成功");
            }
            List<Room> allRoom = roomMapperImpl.selectRoomAll();
            jsonObject.put("result",result);
            jsonObject.put("allRoom",allRoom);
            return jsonObject;
        }
    }


}
