package com.ahav.reserve.service;

import com.ahav.reserve.pojo.Room;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

public interface IRoomService {
    //修改pub模板（查看会议室的pub模板）
    public JSONObject selectRoomPubTemplate(int room);
    //根据会议室ID查询出会议室姓名
    public String findRoomName(Integer roomId);
}
