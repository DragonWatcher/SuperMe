package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Room")
public class Room {
    @ApiModelProperty(value = "会议室Id")
    private Integer meetingRoomId;
    @ApiModelProperty(value = "会议室规模")
    private Integer meetingRoomScale;
    @ApiModelProperty(value = "会议室名称")
    private String meetingRoomName;
    @ApiModelProperty(value = "会议室设备列表")
    private String meetingEquipmentList;
    @ApiModelProperty(value = "会议室状态")
    private Integer meetingRoomStatus;
    @ApiModelProperty(value = "本会议室所对应的Pub的ip")
    private String pubIp;
    @ApiModelProperty(value = "本会议室对应的pub模板列表")
    private String presetmodeList;

    public void setMeetingEquipmentList(String meetingEquipmentList) {
        this.meetingEquipmentList = meetingEquipmentList;
    }

    public String getPubIp() {
        return pubIp;
    }

    public void setPubIp(String pubIp) {
        this.pubIp = pubIp;
    }

    public String getPresetmodeList() {
        return presetmodeList;
    }

    public void setPresetmodeList(String presetmodeList) {
        this.presetmodeList = presetmodeList;
    }

    public Integer getMeetingRoomId() {
        return meetingRoomId;
    }

    public void setMeetingRoomId(Integer meetingRoomId) {
        this.meetingRoomId = meetingRoomId;
    }

    public Integer getMeetingRoomScale() {
        return meetingRoomScale;
    }

    public void setMeetingRoomScale(Integer meetingRoomScale) {
        this.meetingRoomScale = meetingRoomScale;
    }

    public String getMeetingRoomName() {
        return meetingRoomName;
    }

    public void setMeetingRoomName(String meetingRoomName) {
        this.meetingRoomName = meetingRoomName == null ? null : meetingRoomName.trim();
    }

    public String getMeetingEquipmentList() {
        return meetingEquipmentList;
    }

    public void setMeeingEquipmentList(String meetingEquipmentList) {
        this.meetingEquipmentList = meetingEquipmentList == null ? null : meetingEquipmentList.trim();
    }

    public Integer getMeetingRoomStatus() {
        return meetingRoomStatus;
    }

    public void setMeetingRoomStatus(Integer meetingRoomStatus) {
        this.meetingRoomStatus = meetingRoomStatus;
    }

    @Override
    public String toString() {
        return "Room{" +
                "meetingRoomId=" + meetingRoomId +
                ", meetingRoomScale=" + meetingRoomScale +
                ", meetingRoomName='" + meetingRoomName + '\'' +
                ", meetingEquipmentList='" + meetingEquipmentList + '\'' +
                ", meetingRoomStatus=" + meetingRoomStatus +
                ", pubIp='" + pubIp + '\'' +
                ", presetmodeList='" + presetmodeList + '\'' +
                '}';
    }
}