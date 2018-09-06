package com.ahav.reserve.service;

import com.ahav.reserve.pojo.MeetingDetails;
import com.ahav.reserve.pojo.PubTemplate;
import com.ahav.reserve.pojo.Result;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;
import java.util.Map;

public interface IMeetingDetailsService {
    //初始化预约管理页面
    public Map findMeetingDetailsAll();
    //保存修改会议详情
    public Map alterMeetingDetails(MeetingDetails meetingDetails,PubTemplate pubTemplate);
    //删除会议详情(取消会议预约)
    public Result deleteMeetingDetails(Integer deDetailsId);
    //根据详情Id查询会议详情
    public Map findMeetingDetails(Integer deDetailsId);
    //按条件查询会议详情
    public Map findMeetingDetails(MeetingDetails meetingDetails);
    //查询下一次会议的时间
    public MeetingDetails findNextTime(Date endTime);
    //查询上一次会议的时间
    public MeetingDetails findLastTime(Date startTime);
    //添加会议详情//添加会议详情
    public JSONObject addMeetingDetails(MeetingDetails meetingDetails,PubTemplate pubTemplate);
    //调用aqi加载pub模板
    public void loadPubTemplateCon();
    //查看会议当前模板
    public JSONObject findMeetingPubTemplate(int deDetailsId);
    //保存模板
    public Map saveTemplate(MeetingDetails meetingDetails,PubTemplate pubTemplate);
    //根据预定人姓名查询预定人ID
    public int findReserveId(String reserveName);

    //修改会议详情
    JSONObject alterMeetingDetails();
    //添加会议详情
    JSONObject addMeetingDetails();
    //历史查询
    JSONObject selectHistory(MeetingDetails meetingDetails,Integer pageNum,Integer pageSize);
    //删除pub模板
    Map deletePubTemplate(Integer deDetailsId,String pubTemplate);
    //根据设备列表查询相应的会议详情
    JSONObject byEquipmentListSelectMeetingDetails(String[] equipmentList,Date todayTime);
}
