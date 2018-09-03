package com.ahav.reserve.service;

import com.ahav.reserve.pojo.RoomSettings;
import com.alibaba.fastjson.JSONObject;

public interface IRoomService {

    //修改pub模板（查看会议室的pub模板）
    public JSONObject selectRoomPubTemplate(int deDetailsId);

    //查询所有会议室
    public JSONObject selectAllRoom();

    //通过会议室id查询会议室
    public JSONObject selectRoomById(int meetingRoomId);

    //保存会议室设置
    JSONObject saveRoomSettings(RoomSettings roomSettings);

    //继续并添加模板---通过会议室id查询该会议的pub模板
    JSONObject byRoomIdSelectRoomPubTemplate(Integer roomId);
}
