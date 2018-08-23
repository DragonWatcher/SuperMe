package com.ahav.reserve.serviceimpl;

import com.ahav.reserve.mapper.MeetingDetailsMapper;
import com.ahav.reserve.mapper.RoomMapper;
import com.ahav.reserve.pojo.*;
import com.ahav.reserve.service.IMeetingDetailsService;
import com.ahav.reserve.utils.meetingUtils;
import com.ahav.system.entity.*;
import com.ahav.system.service.DeptService;
import com.ahav.system.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class MeetingDetailsServiceImpl implements IMeetingDetailsService {
    @Autowired
    private MeetingDetailsMapper meetingDetailsMapperImpl;
    @Autowired
    private RoomMapper RoomMapperImpl;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private DeptService deptServiceImpl;
    @Autowired
    private UserService userServiceImpl;

    private static int i = 0;//会议遍历序号

    //查询所有会议详情（初始化界面）
    @Override
    public Map findMeetingDetailsAll() {
        Result result = new Result();

        Map initPageMap = new HashMap();
        MeetingTime meetingTime = new MeetingTime();

        //调用接口参数当前操作需要的权限，得到true或false
            /*flag = 调用是否有权限的接口*/
        //TODO:调用接口获得当前用户的id
        SystemResult currentUser = userServiceImpl.getCurrentUser();
        System.out.println(currentUser);
        SimpleUser user = (SimpleUser)currentUser.getData();
        System.out.println(user);
        int deReserveId = user.getUserId();
        Boolean flag = true;
/*        int deReserveId = 3;  //预定人id*/

        //查询出当天所有的会议
        MeetingDetails mDetails =  new MeetingDetails();
        Date startTime = meetingUtils.getStartTime(new Date());
        Date endTime = meetingUtils.getEndTime(new Date());
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);

        for(MeetingDetails meetingDetails:meetingDetailsAll){
            //根据会议室ID查询出会议室的名称，并设置到MeetingDetails对象中
            String roomName = RoomMapperImpl.selectRoomName(meetingDetails.getDeRoomId());
            meetingDetails.setDeRoomName(roomName);
            //TODO：调用接口：根据部门id查询部门名称，并设置到MeetingDetails对象中
            Dept dept = deptServiceImpl.getDeptById(meetingDetails.getDeReserveDepartmentId());
            meetingDetails.setDeReserveDepartment(dept.getDeptName());
            /*TODO:调用接口：根据预订人id查询出预订人的姓名，并设置到MeetingDetails对象中*/
            SimpleUser userById = (SimpleUser)userServiceImpl.getUserById(meetingDetails.getDeReserveId()).getData();
            meetingDetails.setDeReserve(userById.getTrueName());
        }



        //获得上次会议时间
        MeetingDetails lastTime = findLastTime(startTime);
        if(lastTime != null){
            //只有上次会议不为空，才取会议时间
            meetingTime.setLastMeetingTime(lastTime.getDeMeetingStart());
        }
        //获得下次会议时间
        MeetingDetails nextTime = findNextTime(endTime);
        if(nextTime != null){
            //只有下次会议不为空，才取会议时间
            meetingTime.setNextMeetingTime(nextTime.getDeMeetingStart());
        }

        List<Room> roomAll = RoomMapperImpl.selectRoomAll();
        initPageMap.put("roomAll",roomAll);
        initPageMap.put("meetingTime",meetingTime);

        /*TODO:调用接口查询会议室设备列表
        * initPageMap.put("会议室设备列表",会议室设备列表);*/


        if(flag){
            //可以查看所有会议详情
            for(MeetingDetails meetingDetails:meetingDetailsAll){
                meetingDetails.setDeShow(1);
            }

            initPageMap.put("meetingDetailsAll",meetingDetailsAll);

            result.setStatus(200);
            initPageMap.put("result",result);
            return initPageMap;
        }else{
            //不可查看所有会议详情
            for(MeetingDetails meetingDetails:meetingDetailsAll){
                if(meetingDetails.getDeReserveId()==deReserveId){
                    meetingDetails.setDeShow(1);
                }
            }
            initPageMap.put("meetingDetailsAll",meetingDetailsAll);
            result.setStatus(200);
            initPageMap.put("result",result);
            return initPageMap;
        }

    }





/*  //调用模板
    public void findTemplate(){
        RestTemplate.getForObject("http://192.168.18.112:80/aqi/presetmode/list",JSONObject.class);
    }*/

    //修改会议详情
    @Override
    public JSONObject alterMeetingDetails() {
        //查询出所有会议室
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        List<Room> rooms = RoomMapperImpl.selectRoomAll();
        /*TODO:调用接口查询出所有部门*/
        SystemResult systemResult = deptServiceImpl.allDepts();
        List<Dept> allDepts =(List<Dept>) systemResult.getData();
        if(rooms.size() > 0){
            result.setStatus(200);
            jsonObject.put("allDepts",allDepts);
            jsonObject.put("rooms",rooms);
            jsonObject.put("result",result);

            return jsonObject;
        }else{
            result.setStatus(400);
            jsonObject.put("result",result);
            return jsonObject;
        }
    }

    //保存修改会议详情
    @Override
    public Map alterMeetingDetails(MeetingDetails meetingDetails) {
        String deptReservePersonId = null; //部门预定人的id
        Map map = new HashMap<String,Object>();
        Result result = new Result();
        int deRoomId = meetingDetails.getDeRoomId();
        int reserveId = meetingDetails.getDeReserveId();

        if(reserveId > 0){
            meetingDetails.setDeReserveId(reserveId);
            Date stratTime = meetingDetails.getDeMeetingStart(); //修改为的会议开始时间
            Date overTime = meetingDetails.getDeMeetingOver();  //修改为的会议结束时间
            MeetingDetails mDetails = new MeetingDetails();
            boolean flag = false;

            //查询出指定日期指定会议室的会议详情
            Date startTime = meetingUtils.getStartTime(stratTime); //获得指定日期的开始时间
            Date endTime = meetingUtils.getEndTime(overTime);  //获得指定日期的结束时间
            mDetails.setDeMeetingStart(startTime);
            mDetails.setDeMeetingOver(endTime);
            mDetails.setDeRoomId(deRoomId);
            //查询出不包含当前会议的所有会议
            mDetails.setDeDetailsId(meetingDetails.getDeDetailsId());
            List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);
            //List<MeetingDetails> roomIdMeetingDetails = meetingDetailsMapperImpl.selectRoomIdMeetingDetails(deRoomId);
            //判断修改的会议时间跟以有的会议时间有没有冲突
            
            if(meetingDetailsAll.size()>0){
                //如果查到了会议则按个比较会议时间有没有冲突
                for (MeetingDetails md:meetingDetailsAll){
                    if(md.getDeMeetingStart().compareTo(stratTime) == -1){
                        if(md.getDeMeetingOver().compareTo(stratTime) == -1){
                            flag=true;
                        }else {
                            flag=false;
                        }
                    }else {
                        if(md.getDeMeetingStart().compareTo(overTime) == 1){
                            flag = true;
                        }else {
                            flag = false;
                        }
                    }
                }
            }else{
                //没有查到指定条件下有会议，所以可以直接修改
                flag=true;
            }
            

            if(flag){
                //TODO:调用接口根据部门id查询出部门预订人的id
                List<User> users = userServiceImpl.selectUserByDeptIdAndRoleId(meetingDetails.getDeReserveDepartmentId(), 3);
                int count =0;
                for(User user:users){
                    if(count == 0){
                        deptReservePersonId = user.getUserId()+"";
                    }else{
                        deptReservePersonId = deptReservePersonId+","+user.getUserId();
                    }
                    count++;
                }
                meetingDetails.setDeDepartmentReservePersonId(deptReservePersonId);
                //时间没有冲突可以修改
                int i = meetingDetailsMapperImpl.updateByPrimaryKeySelective(meetingDetails);
                if(i>0){
                    result.setStatus(200);
                    MeetingDetails meetingDetails1 = meetingDetailsMapperImpl.selectByPrimaryKey(meetingDetails.getDeDetailsId());
                    //根据会议室ID查询出会议室名称
                    Integer deRoomId1 = meetingDetails1.getDeRoomId();
                    String roomName = RoomMapperImpl.selectRoomName(deRoomId1);
                    meetingDetails1.setDeRoomName(roomName);
                    /*TODO:调用接口：根据预订人的Id查询预定人姓名，并设置到MeetingDetails对象中*/
                    SimpleUser userById = (SimpleUser)userServiceImpl.getUserById(meetingDetails.getDeReserveId()).getData();
                    meetingDetails1.setDeReserve(userById.getTrueName());
                    /*TODO:调用接口：根据部门id查询出部门姓名，并设置到MeetingDetails对象中*/
                    Dept dept = deptServiceImpl.getDeptById(meetingDetails.getDeReserveDepartmentId());
                    meetingDetails1.setDeReserveDepartment(dept.getDeptName());
                    map.put("result",result);
                    map.put("meetingDetails1",meetingDetails1);
                    return map;
                }else{
                    result.setStatus(400);
                    map.put("result",result);
                    return map;
                }

            }else {
                //时间冲突
                result.setStatus(400);
                map.put("result",result);
                return map;
            }
        }else{
            //没有这个预定人
            result.setStatus(400);
            map.put("result",result);
            return map;
        }

    }

    //删除会议详情（取消预约）
    @Override
    public Result deleteMeetingDetails(Integer deDetailsId) {
        int i = meetingDetailsMapperImpl.deleteByPrimaryKey(deDetailsId);
        Result result = new Result();
        if(i>0){
            result.setStatus(200);
        }else{
            result.setStatus(400);
        }
        return result;
    }

    //根据详情id查询会议详情
    @Override
    public Map findMeetingDetails(Integer deDetailsId) {
        Map detailsMap = new HashMap<String,Object>();
        Result result = new Result();
        if(deDetailsId > 0){
            //当传递
            MeetingDetails meetingDetails = meetingDetailsMapperImpl.selectByPrimaryKey(deDetailsId);
            if(meetingDetails != null){
                //获得查询会议的名称，并添加到MeetingDetails对象中
                meetingDetails.setDeRoomName(RoomMapperImpl.selectRoomName(meetingDetails.getDeRoomId()));

                result.setStatus(200);
                detailsMap.put("result",result);
                detailsMap.put("meetingDetails",meetingDetails);
            }else {
                result.setStatus(400);
                detailsMap.put("result",result);
            }
            return detailsMap;
        }else {
            result.setStatus(400);
            detailsMap.put("result",result);
            return detailsMap;
        }
    }

    //按条件查询会议详情
    @Override
    public Map findMeetingDetails(MeetingDetails meetingDetails) {
        Result result = new Result();
        Boolean flag = false; //默认没有权限
        Map meetingListMap = new HashMap<String,Object>();
        MeetingTime meetingTime = new MeetingTime(); //会议时间
        String reserveName = meetingDetails.getDeReserve(); //获得前台传递的预定人姓名
        String meetingName = meetingDetails.getDeMeetingName(); //会议详情的名称
        List<MeetingDetails> meetingDetailsList = new ArrayList<>();

        //根据条件查询出某一天的会议详情
        Date meetingStart = meetingDetails.getDeMeetingStart();
        Date startTime = meetingUtils.getStartTime(meetingStart);  //获得某一天的开始时间
        Date endTime = meetingUtils.getEndTime(meetingStart);  //获得某一天的结束时间
        meetingDetails.setDeMeetingStart(startTime);
        meetingDetails.setDeMeetingOver(endTime);

        if(meetingName != null && "" != meetingName){
            meetingDetails.setDeMeetingName(meetingName);
        }
        if(reserveName != null &&  "" != reserveName ){
            meetingDetails.setDeReserve(reserveName);
        }

       // TODO:如果预订人不等于Null则调用接口：根据从前台获取的预定人姓名模糊查询出所有符合条件的预定人ID
            //思路遍历预定人的i
               if(reserveName != null && "" != reserveName){
                    List<SimpleUser> users = (List<SimpleUser>) userServiceImpl.getUserByTrueName(reserveName).getData();
                    List<MeetingDetails>  meetingDetails1 = meetingDetailsMapperImpl.byReserveIdselectMeetingDetails(users.get(i).getUserId());
                    for(int i=0;meetingDetails1.size()>i;i++){
                        /*meetingDetails.setDeReserveId(users.get(i).getUserId());
                        List<MeetingDetails> meetingDetailsList1=meetingDetailsMapperImpl.selectMeetingDetails(meetingDetails);*/
                        meetingDetailsList.add(meetingDetails1.get(i));
                 }
             }else{
                meetingDetailsList = meetingDetailsMapperImpl.selectMeetingDetails(meetingDetails);
             }


        //  List<MeetingDetails> meetingDetailsList = meetingDetailsMapperImpl.selectMeetingDetails(meetingDetails);
        for(MeetingDetails meetingDetails1 :meetingDetailsList){
            //调用接口：根据会议室Id查询出会议室的名称，并设置到MeetingDetails对象中
            meetingDetails1.setDeRoomName(RoomMapperImpl.selectRoomName(meetingDetails1.getDeRoomId()));
            //TODO:调用接口：根据部门id查询部门名称，并设置到MeetingDetails对象中
            Dept dept = deptServiceImpl.getDeptById(meetingDetails1.getDeReserveDepartmentId());
            meetingDetails1.setDeReserveDepartment(dept.getDeptName());
            /*meetingDetails1.setDeReserveDepartment("销售部");*/
            /*TODO:调用接口：根据预订人id查询出预定人的姓名，并设置到MeetingDetails对象中*/
            SimpleUser userById = (SimpleUser)userServiceImpl.getUserById(meetingDetails1.getDeReserveId()).getData();
            meetingDetails1.setDeReserve(userById.getTrueName());
        }

        //TODO:调用接口参数当前操作需要的权限，得到true或false
            /*flag = 调用是否有权限的接口*/
        //TODO:调用接口token获得当前用户的id
        SystemResult currentUser = userServiceImpl.getCurrentUser();
        SimpleUser user = (SimpleUser)currentUser.getData();
        int deReserveId = user.getUserId();
        /*int deReserveId = 2;*/

        //获得上次会议时间
        MeetingDetails lastTime = meetingDetailsMapperImpl.selectLastTime(startTime);
        if(lastTime != null){
            meetingTime.setLastMeetingTime(lastTime.getDeMeetingStart());
            //System.out.println(meetingTime.getLastMeetingTime());
        }
        //获得下次会议时间
        MeetingDetails nextTime = meetingDetailsMapperImpl.selectNextTime(endTime);
        if(nextTime != null){
            meetingTime.setNextMeetingTime(nextTime.getDeMeetingStart());
            //System.out.println(meetingTime.getNextMeetingTime());
        }

        meetingListMap.put("meetingTime ",meetingTime);  //将会议时间对象添加到map中

        if(flag){
            //有查看别人的权限
            for(MeetingDetails md:meetingDetailsList){
                md.setDeShow(1);
            }
            meetingListMap.put("meetingDetailsList",meetingDetailsList);
            result.setStatus(200);
            meetingListMap.put("result",result);
            return meetingListMap;
        }else{
            //没有查看别人的权限
            for(MeetingDetails md:meetingDetailsList){
                if(md.getDeReserveId() == deReserveId){
                    md.setDeShow(1);
                }
            }
            meetingListMap.put("meetingDetailsList",meetingDetailsList);
            result.setStatus(200);
            meetingListMap.put("result",result);
            return meetingListMap;
        }
    }

    //查询上次会议的时间
    @Override
    public MeetingDetails findNextTime(Date startTime) {
        return meetingDetailsMapperImpl.selectNextTime(startTime);
    }
    //查询下次会议的时间
    @Override
    public MeetingDetails findLastTime(Date endTime) {
        return meetingDetailsMapperImpl.selectLastTime(endTime);
    }


    /*    //根据会议室id查询会议详情
        @Override
        public Map findRoomIdMeetingDetails(Integer deRoomId) {
            Map detailsMap = new HashMap<String,Object>();
            Result result = new Result();
            if(deDetailsId > 0){
                //当传递
                MeetingDetails meetingDetails = meetingDetailsMapperImpl.findMeetingDetails(deDetailsId);
                if(meetingDetails != null){
                    result.setStatus(200);
                    detailsMap.put("meetingDetails",meetingDetails);
                }else {
                    result.setStatus(400);
                    detailsMap.put("meetingDetails",meetingDetails);
                }
                return detailsMap;
            }else {
                result.setStatus(400);
                detailsMap.put("result",result);
                return detailsMap;
            }
        }*/

    //添加会议详情
    @Override
    public JSONObject addMeetingDetails() {
        //查询出所有会议室
        Result result = new Result();
        JSONObject jsonObject = new JSONObject();
        List<Room> rooms = RoomMapperImpl.selectRoomAll();
        //TODO:调用接口：查询出所有部门信息
        SystemResult systemResult = deptServiceImpl.allDepts();
        List<Dept> allDepts =(List<Dept>) systemResult.getData();
        if(rooms.size() > 0){
            result.setStatus(200);
            jsonObject.put("allDepts",allDepts);
            jsonObject.put("rooms",rooms);
            jsonObject.put("result",result);
            return jsonObject;
        }else{
            result.setStatus(400);
            jsonObject.put("result",result);
            return jsonObject;
        }

    }

    //保存添加会议详情
    @Override
    public Result addMeetingDetails(MeetingDetails meetingDetails) {
        String deptReservePersonId = null; //部门预定人的id
        Result result = new Result();
        MeetingDetails mDetails =  new MeetingDetails();
        int roomId = meetingDetails.getDeRoomId();
        int deReserveId = meetingDetails.getDeReserveId();

        boolean flag = false;
        Date meetingStart = meetingDetails.getDeMeetingOver(); //添加新会议的开始时间
        Date meetingOver = meetingDetails.getDeMeetingStart();  //添加新会议的结束时间
        
        //TODO:调用接口根据部门id查询出部门预订人的id
        List<User> users = userServiceImpl.selectUserByDeptIdAndRoleId(meetingDetails.getDeReserveDepartmentId(), 3);
        int count =0;
        for(User user:users){
            if(count == 0){
                deptReservePersonId = user.getUserId()+"";
            }else{
                deptReservePersonId = deptReservePersonId+","+user.getUserId();
            }
            count++;
        }
        meetingDetails.setDeDepartmentReservePersonId(deptReservePersonId);
        
        //查询出指定日期指定会议室的会议详情
        Date startTime = meetingUtils.getStartTime(meetingStart); //获得指定日期的开始时间
        Date endTime = meetingUtils.getEndTime(meetingOver);  //获得指定日期的结束时间
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        mDetails.setDeRoomId(roomId);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);
        //判断指定日期指定的会议室已有会议有没有跟新添加会议的时间冲突
        if(meetingDetailsAll.size()>0){
            for (MeetingDetails md:meetingDetailsAll){
                if(md.getDeMeetingStart().compareTo(meetingStart) == -1){
                    if(md.getDeMeetingOver().compareTo(meetingStart) == -1){
                        flag=true;
                    }else {
                        flag=false;
                    }
                }else {
                    if(md.getDeMeetingStart().compareTo(meetingOver) == 1){
                        flag = true;
                    }else {
                        flag = false;
                    }
                }
            }
        }else{
            flag = true;
        }
        

        if(flag){
            //没有冲突,添加会议
            if(deReserveId > 0){
                int update = meetingDetailsMapperImpl.insertSelective(meetingDetails);
                if(update == 1){
                    result.setStatus(200);
                    return result;
                }else {
                    result.setStatus(400);
                    return result;
                }
            }else{
                result.setStatus(400);
                return result;
            }

        }else{
            result.setStatus(400);
            return result;
        }
    }


    //加载pub模板（）
    @Override
    public void loadPubTemplateCon() {
        //遍历序号

        //查询出当天所有会议
        MeetingDetails mDetails =  new MeetingDetails();
        Date currentTime = new Date();//当前时间
        Date startTime = meetingUtils.getStartTime(currentTime);
        Date endTime = meetingUtils.getEndTime(currentTime);
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);

        //对时间进行格式化
        DateFormat b=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        //第i个会议详情
        MeetingDetails meetingDetails = meetingDetailsAll.get(i);
        Date meetingStartTime = meetingDetails.getDeMeetingStart();
        try {
            currentTime = b.parse(b.format(currentTime));
            meetingStartTime = b.parse(b.format(meetingStartTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //会议个数
        int meetingCount = meetingDetailsAll.size();
        //遍历所有会议，取出会议的开时间跟当前时间比较，如果相等则调用加载pub模板的aqi
        if(meetingStartTime == currentTime){
            i++;
            /*判断当前是否是无模板，如果是则不去调用加载pub模板api*/
            //调用pub加载pub模板的aqi
                //获取pubIp
            Integer roomId = meetingDetails.getDeRoomId();
            String pubIp = RoomMapperImpl.selectByPrimaryKey(roomId).getPubIp();
            //获取模板id
            String dePubTemplate = meetingDetails.getDePubTemplate();
            PubTemplate pubTemplate = JSON.parseObject(dePubTemplate, PubTemplate.class);//将json字符串转为对应的对象
            //调用pub加载pub模板的aqi
            restTemplate.getForEntity("http://"+pubIp+"/ajax/presetmode/load?Id="+pubTemplate.getId(), String.class).getBody();
        }
        //如果会议序号变成了当天会议总数减1就代表，当天的会议遍历完了那么会序号置为0开始等待开始比较下一天的会议
        if(i==meetingCount-1){
            i=0;
        }

    }


    //查看会议当前模板
    @Override
    public JSONObject findMeetingPubTemplate(int deDetailsId) {
        JSONObject jsonObject = new JSONObject();
        Result result = new Result();
        MeetingDetails meetingDetails = meetingDetailsMapperImpl.selectByPrimaryKey(deDetailsId);
        if(meetingDetails != null){
            String dePubTemplate = meetingDetails.getDePubTemplate();
            result.setStatus(200);
            jsonObject.put("result",result);
            jsonObject.put("dePubTemplate",dePubTemplate);
            return jsonObject;
        }else{
            result.setStatus(400);
            return jsonObject;
        }
    }

    //保存模板
    @Override
    public Result saveTemplate(PubTemplate pubTemplate,int deDetailsId) {
        Result result = new Result();
        //将对象转为json字符串
        String jsonTemplate = JSON.toJSONString(pubTemplate);
        int update = meetingDetailsMapperImpl.updatePubTemplate(jsonTemplate,deDetailsId);
        if(update > 0){
            result.setStatus(200);
        }else {
            result.setStatus(400);
        }
        return result;
    }

    //根据预定人姓名查询预定人ID
    @Override
    public int findReserveId(String reserveName) {
        return meetingDetailsMapperImpl.selectReserveId(reserveName);
    }



}
