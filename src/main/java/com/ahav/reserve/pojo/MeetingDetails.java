package com.ahav.reserve.pojo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Arrays;
import java.util.Date;

@ApiModel(description = "MeetingDetails")
public class MeetingDetails {
    @ApiModelProperty(value = "会议详情id")
    private Integer id;  //前台规定必须叫id
    @ApiModelProperty(value = "会议级别")
    private String deGrade;
    @ApiModelProperty(value = "会议室id")
    private Integer deRoomId;
    @ApiModelProperty(value = "会议室名称")
    private String deRoomName;  //仅在类中，表中不含该字段
    @ApiModelProperty(value = "会议详情名称")
    private String deMeetingName;
    @ApiModelProperty(value = "预定部门")
    private String deReserveDepartment;
    @ApiModelProperty(value = "预定部门Id")
    private String deReserveDepartmentId;  //
    @ApiModelProperty(value = "预定人")
    private String deReserve;
    @ApiModelProperty(value = "预定人电话")
    private String deReservePhone;
    @ApiModelProperty(value = "预定人数")
    private Integer deReserveNumber;
    @ApiModelProperty(value = "主要人员")
    private String deMain;
    @ApiModelProperty(value = "会议备注")
    private String text;
    @ApiModelProperty(value = "会议结束时间")
    private Date deMeetingOver;
    @ApiModelProperty(value = "会议开始时间")
    private Date deMeetingStart;
    @ApiModelProperty(value = "部门预定人")
    private String[] deDepartmentReservePerson;
    @ApiModelProperty(value = "会议室占用状态，是否释放")
    private String deMeetingStatus;
    @ApiModelProperty(value = "场次共计")
    private Integer deMeetingCount;
    @ApiModelProperty(value = "用时共计")
    private Date deDateCount;
    @ApiModelProperty(value = "会议级别Id")
    private Integer deGradeId;
    @ApiModelProperty(value = "预定人Id")
    private Integer deReserveId;
    @ApiModelProperty(value = "部门预定人id")
    private String deDepartmentReservePersonId;
    @ApiModelProperty(value = "是否展示会议")
    private Integer deShow;
    @ApiModelProperty(value = "pub模板")
    private String dePubTemplate;

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

    public String getDeRoomName() {
        return deRoomName;
    }

    public void setDeRoomName(String deRoomName) {
        this.deRoomName = deRoomName;
    }

    public String getDeReserveDepartmentId() {
        return deReserveDepartmentId;
    }

    public void setDeReserveDepartmentId(String deReserveDepartmentId) {
        this.deReserveDepartmentId = deReserveDepartmentId;
    }

    public String getDePubTemplate() {
        return dePubTemplate;
    }

    public void setDePubTemplate(String dePubTemplate) {
        this.dePubTemplate = dePubTemplate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDeGrade() {
        return deGrade;
    }

    public void setDeGrade(String deGrade) {
        this.deGrade = deGrade == null ? null : deGrade.trim();
    }

    public Integer getDeRoomId() {
        return deRoomId;
    }

    public void setDeRoomId(Integer deRoomId) {
        this.deRoomId = deRoomId;
    }

    public String getDeMeetingName() {
        return deMeetingName;
    }

    public void setDeMeetingName(String deMeetingName) {
        this.deMeetingName = deMeetingName == null ? null : deMeetingName.trim();
    }

    public String getDeReserveDepartment() {
        return deReserveDepartment;
    }

    public void setDeReserveDepartment(String deReserveDepartment) {
        this.deReserveDepartment = deReserveDepartment == null ? null : deReserveDepartment.trim();
    }

    public String getDeReserve() {
        return deReserve;
    }

    public void setDeReserve(String deReserve) {
        this.deReserve = deReserve == null ? null : deReserve.trim();
    }

    public String getDeReservePhone() {
        return deReservePhone;
    }

    public void setDeReservePhone(String deReservePhone) {
        this.deReservePhone = deReservePhone == null ? null : deReservePhone.trim();
    }

    public Integer getDeReserveNumber() {
        return deReserveNumber;
    }

    public void setDeReserveNumber(Integer deReserveNumber) {
        this.deReserveNumber = deReserveNumber;
    }

    public String getDeMain() {
        return deMain;
    }

    public void setDeMain(String deMain) {
        this.deMain = deMain == null ? null : deMain.trim();
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDeMeetingOver() {
        return deMeetingOver;
    }

    public void setDeMeetingOver(Date deMeetingOver) {
        this.deMeetingOver = deMeetingOver;
    }

    public Date getDeMeetingStart() {
        return deMeetingStart;
    }

    public void setDeMeetingStart(Date deMeetingStart) {
        this.deMeetingStart = deMeetingStart;
    }

    public String[] getDeDepartmentReservePerson() {
        return deDepartmentReservePerson;
    }

    public void setDeDepartmentReservePerson(String[] deDepartmentReservePerson) {
        this.deDepartmentReservePerson = deDepartmentReservePerson;
    }

    public String getDeMeetingStatus() {
        return deMeetingStatus;
    }

    public void setDeMeetingStatus(String deMeetingStatus) {
        this.deMeetingStatus = deMeetingStatus == null ? null : deMeetingStatus.trim();
    }

    public Integer getDeMeetingCount() {
        return deMeetingCount;
    }

    public void setDeMeetingCount(Integer deMeetingCount) {
        this.deMeetingCount = deMeetingCount;
    }

    public Date getDeDateCount() {
        return deDateCount;
    }

    public void setDeDateCount(Date deDateCount) {
        this.deDateCount = deDateCount;
    }

    public Integer getDeGradeId() {
        return deGradeId;
    }

    public void setDeGradeId(Integer deGradeId) {
        this.deGradeId = deGradeId;
    }

    public Integer getDeReserveId() {
        return deReserveId;
    }

    public void setDeReserveId(Integer deReserveId) {
        this.deReserveId = deReserveId;
    }

    public String getDeDepartmentReservePersonId() {
        return deDepartmentReservePersonId;
    }

    public void setDeDepartmentReservePersonId(String deDepartmentReservePersonId) {
        this.deDepartmentReservePersonId = deDepartmentReservePersonId;
    }

    public Integer getDeShow() {
        return deShow;
    }

    public void setDeShow(Integer deShow) {
        this.deShow = deShow;
    }

    @Override
    public String toString() {
        return "MeetingDetails{" + "id=" + id + ", deGrade='" + deGrade + '\'' + ", deRoomId=" + deRoomId + ", deRoomName='" + deRoomName + '\'' + ", deMeetingName='" + deMeetingName + '\'' + ", deReserveDepartment='" + deReserveDepartment + '\'' + ", deReserveDepartmentId='" + deReserveDepartmentId + '\'' + ", deReserve='" + deReserve + '\'' + ", deReservePhone='" + deReservePhone + '\'' + ", deReserveNumber=" + deReserveNumber + ", deMain='" + deMain + '\'' + ", text='" + text + '\'' + ", deMeetingOver=" + deMeetingOver + ", deMeetingStart=" + deMeetingStart + ", deDepartmentReservePerson=" + Arrays.toString(deDepartmentReservePerson) + ", deMeetingStatus='" + deMeetingStatus + '\'' + ", deMeetingCount=" + deMeetingCount + ", deDateCount=" + deDateCount + ", deGradeId=" + deGradeId + ", deReserveId=" + deReserveId + ", deDepartmentReservePersonId='" + deDepartmentReservePersonId + '\'' + ", deShow=" + deShow + ", dePubTemplate='" + dePubTemplate + '\'' + ", start_date='" + start_date + '\'' + ", end_date='" + end_date + '\'' + '}';
    }
}