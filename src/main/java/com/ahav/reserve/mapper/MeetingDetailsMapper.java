package com.ahav.reserve.mapper;

import com.ahav.reserve.pojo.MeetingDetails;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Mapper
@Repository
public interface MeetingDetailsMapper {
    int deleteByPrimaryKey(Integer deDetailsId);

    int insert(MeetingDetails record);

    int insertSelective(MeetingDetails record);
    //根据会议详情id查询会议详情
    MeetingDetails selectByPrimaryKey(Integer deDetailsId);

    int updateByPrimaryKeySelective(MeetingDetails record);

    int updateByPrimaryKey(MeetingDetails record);

    //查询下一次会议时间
    MeetingDetails selectNextTime(Date startTime);
    //查询上一次会议时间
    MeetingDetails selectLastTime(Date endTime);
    //查询所有会议详情(初始化界面)
    List<MeetingDetails> selectMeetingDetailsAll();
    //按条件查询会议详情
    List<MeetingDetails> selectMeetingDetails(MeetingDetails meetingDetails);

    //保存pub模板
    public int updatePubTemplate(String jsonStringPubTemplate,int deDetailsId);
    //根据预定人姓名查询预定人ID
    public int selectReserveId(String reserveName);
    //根据预订人id查询会议详情
    public List<MeetingDetails> byReserveIdselectMeetingDetails(Integer ReserveId);
    //历史查询
    List<MeetingDetails> selectHistory (MeetingDetails meetingDetails);
    //保存修改会议详情，排除当前会议
    List<MeetingDetails> byExcludeDetailsIdselectMeetingDetails(MeetingDetails mDetails);
    //删除pub模板
    int deletePubTemplate(int deDetailsId,String pubTemplate);
    //获取刚插入的会议详情的id
    Integer selectNewestDetailsId();
    //通过时间和所选设备查询符合条件的会议详情
    List<MeetingDetails> byEquipmentListSelectMeetingDetails(Date startTime, Date endTime, List<Integer> excludeRoom);
}