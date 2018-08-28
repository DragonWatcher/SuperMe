package com.ahav.reserve.mapper;

import com.ahav.reserve.pojo.Room;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository

public interface RoomMapper {
    int deleteByPrimaryKey(Integer meetingRoomId);

    int insert(Room record);

    int insertSelective(Room record);

    Room selectByPrimaryKey(Integer meetingRoomId);

    int updateByPrimaryKeySelective(Room record);

    int updateByPrimaryKey(Room record);
    //查詢所有會議室
    List<Room> selectRoomAll();
    //根据会议室ID查询出会议室姓名
    String selectRoomName(Integer roomId);
    //通过会议室名称查询会议室
    Room selectRoomByName(String roomName);


}