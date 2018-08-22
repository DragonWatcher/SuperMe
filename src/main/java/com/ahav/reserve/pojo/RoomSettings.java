package com.ahav.reserve.pojo;

import java.util.List;

//编辑会议室所用的类
public class RoomSettings {
    /** 保留的部门信息列表*/
    private List<Room> roomList;
    /** 删除的部门列表*/
    private List<Room> deleteRoomList;

    public List<Room> getRoomList() {
        return roomList;
    }

    public void setRoomList(List<Room> roomList) {
        this.roomList = roomList;
    }

    public List<Room> getDeleteRoomList() {
        return deleteRoomList;
    }

    public void setDeleteRoomList(List<Room> deleteRoomList) {
        this.deleteRoomList = deleteRoomList;
    }

}
