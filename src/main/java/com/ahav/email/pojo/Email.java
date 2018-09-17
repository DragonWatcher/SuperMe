package com.ahav.email.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value = "邮件设置对象")
public class Email {
    private Integer serverId;
    @ApiModelProperty(value="服务器地址",name="serverAddress",example="smtp.163.com",required = true,dataType="String")
    private String serverAddress;
    @ApiModelProperty(value="邮件类型",name="portocolType",example="smtp",required = true,dataType="String")
    private String portocolType;
    @ApiModelProperty(value="发件箱",name="sender",example="xxx@163.com",required = true,dataType="String")
    private String sender;
    @ApiModelProperty(value="发件箱密码",name="senderPwd",required = true,dataType="String")
    private String senderPwd;
    @ApiModelProperty(value="公司名称",name="company",example="安恒",required = true,dataType="String")
    private String company;
    @ApiModelProperty(value="ssl端口",name="sslPort",example="112",required = false,dataType="Integer")
    private Integer sslPort;
    public Integer getServerId() {
        return serverId;
    }

    public void setServerId(Integer serverId) {
        this.serverId = serverId;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress == null ? null : serverAddress.trim();
    }

    public String getPortocolType() {
        return portocolType;
    }

    public void setPortocolType(String portocolType) {
        this.portocolType = portocolType == null ? null : portocolType.trim();
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender == null ? null : sender.trim();
    }

    public String getSenderPwd() {
        return senderPwd;
    }

    public void setSenderPwd(String senderPwd) {
        this.senderPwd = senderPwd == null ? null : senderPwd.trim();
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    public Integer getSslPort() {
        return sslPort;
    }

    public void setSslPort(Integer sslPort) {
        this.sslPort = sslPort;
    }
}