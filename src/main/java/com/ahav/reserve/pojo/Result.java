package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "MeetingDetails")
public class Result {
    @ApiModelProperty(value = "结果状态")
    private  int status = 400;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                '}';
    }
}
