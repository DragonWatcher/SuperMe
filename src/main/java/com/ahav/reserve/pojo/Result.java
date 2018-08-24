package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "MeetingDetails")
public class Result {
    @ApiModelProperty(value = "结果状态")
    private  int status = 400;
    /** 返回消息*/
    private String message;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Result{" + "status=" + status + ", message='" + message + '\'' + '}';
    }
}
