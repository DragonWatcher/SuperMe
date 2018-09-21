package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModelProperty;

public class TimeOne {
    //返回给前台的时间类型为str
    @ApiModelProperty(value = "会议结束时间Str")
    private String start_date;
    @ApiModelProperty(value = "会议开始时间Str")
    private String end_date;

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }

    @Override
    public String toString() {
        return "Date{" + "start_date='" + start_date + '\'' + ", end_date='" + end_date + '\'' + '}';
    }
}
