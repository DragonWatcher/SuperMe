package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

@ApiModel(description = "MeetingTime")
public class MeetingTime {
    @ApiModelProperty(value = "下次会议时间")
    private Date nextMeetingTime;  //下次会议时间
    @ApiModelProperty(value = "上次会议时间")
    private Date lastMeetingTime;  //上次会议时间

    public Date getNextMeetingTime() {
        return nextMeetingTime;
    }

    public void setNextMeetingTime(Date nextMeetingTime) {
        this.nextMeetingTime = nextMeetingTime;
    }

    public Date getLastMeetingTime() {
        return lastMeetingTime;
    }

    public void setLastMeetingTime(Date lastMeetingTime) {
        this.lastMeetingTime = lastMeetingTime;
    }

    @Override
    public String toString() {
        return "MeetingTime{" +
                "nextMeetingTime=" + nextMeetingTime +
                ", lastMeetingTime=" + lastMeetingTime +
                '}';
    }
}
