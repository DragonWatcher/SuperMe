package com.ahav.email.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
@ApiModel(value = "收件人信息对象")
public class ReceiveMail {
    public ReceiveMail() {
        super();
    }

    @Override
    public String toString() {
        return "ReceiveMail{" +
                "subject='" + subject + '\'' +
                ", content='" + content + '\'' +
                ", receiver=" + Arrays.toString(receiver) +
                ", files=" + Arrays.toString(files) +
                '}';
    }

    //邮件主题
    @ApiModelProperty(value="邮件主题",name="subject",example="软件讨论会",required = true,dataType="String")
    private String subject;
    //邮件内容
    @ApiModelProperty(value="邮件内容",name="content",example="软件讨论会",required = true,dataType="String")
    private String content;
    //收件人
    @ApiModelProperty(value="收件人",name="receiver",example="aa.163.com,bb.163.com",required = true,dataType="String[]")
    private String []receiver;
    //附件
    @ApiModelProperty(value="文件",name="files",example="语音文件路径",required = false,dataType="String[]")
    private String []files;

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String[] getReceiver() {
        return receiver;
    }

    public void setReceiver(String[] receiver) {
        this.receiver = receiver;
    }

    public String[] getFiles() {
        return files;
    }

    public void setFiles(String[] files) {
        this.files = files;
    }

    public ReceiveMail(String subject, String content, String[] receiver, String[] files) {
        this.subject = subject;
        this.content = content;
        this.receiver = receiver;
        this.files = files;
    }
}
