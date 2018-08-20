package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(description = "Token")
public class Token {
    @ApiModelProperty(value = "预定人Id")
    private int deReserveId = 3;
    @ApiModelProperty(value = "预定人")
    private String deReserve;

    public int getDeReserveId() {
        return deReserveId;
    }

    public void setDeReserveId(int deReserveId) {
        this.deReserveId = deReserveId;
    }

    public String getDeReserve() {
        return deReserve;
    }

    public void setDeReserve(String deReserve) {
        this.deReserve = deReserve;
    }

    @Override
    public String toString() {
        return "token{" +
                "deReserveId=" + deReserveId +
                ", deReserve='" + deReserve + '\'' +
                '}';
    }
}
