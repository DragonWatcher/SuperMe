package com.ahav.reserve.serviceimpl;

import com.ahav.reserve.mapper.MeetingDetailsMapper;
import com.ahav.reserve.mapper.RoomMapper;
import com.ahav.reserve.pojo.*;
import com.ahav.reserve.service.IMeetingDetailsService;
import com.ahav.reserve.utils.meetingUtils;
import com.ahav.system.entity.*;
import com.ahav.system.service.DeptService;
import com.ahav.system.service.UserService;
import com.ahav.system.util.CheckPermission;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import static com.ahav.reserve.utils.meetingUtils.transformTimeFormat;


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

    //查询所有会议详情（初始化界面）
    @Override
    public Map findMeetingDetailsAll() {
        Result result = new Result();
        Map initPageMap = new HashMap();
        MeetingTime meetingTime = new MeetingTime();

        //TODO:调用接口获得当前用户的id
        SystemResult currentUser = userServiceImpl.getCurrentUser();
        SimpleUser user = (SimpleUser)currentUser.getData();
        int deReserveId = user.getUserId();
        /*int deReserveId = 18;  //TODO:自测*/
        //TODO:调用接口参数当前操作需要的权限，得到true或false
            /*flag = 调用是否有权限的接口*/
        Boolean flag = CheckPermission.checkPermission("reservation:view:other");
       /* Boolean flag = true; //TODO:自测*/
        //查询出当天所有的会议
        MeetingDetails mDetails =  new MeetingDetails();  //创建查询体
        Date startTime = meetingUtils.getStartTime(new Date());
        Date endTime = meetingUtils.getEndTime(new Date());
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);

        for(MeetingDetails meetingDetails:meetingDetailsAll){

            //将会议开始和结束时间转为指定格式的时间
            meetingDetails.setStart_date(meetingUtils.transformTimeFormat(meetingDetails.getDeMeetingStart()));
            meetingDetails.setEnd_date(meetingUtils.transformTimeFormat(meetingDetails.getDeMeetingOver()));

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
        String date1 = "[\n" + "{ \"id\": \"2\", \"start_date\": \"2018-09-25 11:00:00\", \"end_date\": \"2018-09-25 20:00:00\", \"text\": \"French Open\", \"details\": \"Philippe-Chatrier Court\\n Paris, FRA\"},\n" + "    { \"id\": \"3\", \"start_date\": \"2018-09-25 07:00:00\", \"end_date\": \"2018-09-25 11:00:00\", \"text\": \"Aegon Championship\", \"details\": \"The Queens Club\\n London, ENG\"}]";
        JSONArray date = JSONArray.parseArray(date1);
        initPageMap.put("data",date);
        /*TODO:调用接口查询设备列表(要求前台要求是一个字符串例1,2,3,5)
        * initPageMap.put("设备列表",设备列表);*/

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

        // TODO:如果预订人不等于Null则调用接口：根据从前台获取的预定人姓名模糊查询出所有符合条件的预定人ID
        //思路遍历预定人的id
        if(reserveName != null && "" != reserveName){
            //根据预定人的姓名查询出所有符合条件的预定人id
            List<SimpleUser> users = (List<SimpleUser>) userServiceImpl.getUserByTrueName(reserveName).getData();
            for(SimpleUser user:users){
                meetingDetails.setDeReserveId(user.getUserId());
                //根据第i位用户的id与详情名称查询出所有的详情
                List<MeetingDetails> meetingDetails1 = meetingDetailsMapperImpl.selectMeetingDetails(meetingDetails);
                for(MeetingDetails me1:meetingDetails1){
                    //在吧根据第i位用户的id与详情名称查询出所有的详情，全部添加到List中
                    meetingDetailsList.add(me1);
                }
/*                        List<MeetingDetails>  meetingDetails1 = meetingDetailsMapperImpl.byReserveIdselectMeetingDetails(,);*/
                       /* if(meetingDetails1.size()>0){
                            meetingDetailsList.add(meetingDetails1.get(0));
                        }*/
            }
        }else{
            meetingDetailsList = meetingDetailsMapperImpl.selectMeetingDetails(meetingDetails);
        }



        //  List<MeetingDetails> meetingDetailsList = meetingDetailsMapperImpl.selectMeetingDetails(meetingDetails);
        for(MeetingDetails meetingDetails1 :meetingDetailsList){
            //将会议开始和结束时间转为指定格式的时间
            meetingDetails1.setStart_date(meetingUtils.transformTimeFormat(meetingDetails1.getDeMeetingStart()));
            meetingDetails1.setEnd_date(meetingUtils.transformTimeFormat(meetingDetails1.getDeMeetingOver()));
            //调用接口：根据会议室Id查询出会议室的名称，并设置到MeetingDetails对象中
            meetingDetails1.setDeRoomName(RoomMapperImpl.selectRoomName(meetingDetails1.getDeRoomId()));
            //TODO:调用接口：根据部门id查询部门名称，并设置到MeetingDetails对象中
            Dept dept = deptServiceImpl.getDeptById(meetingDetails1.getDeReserveDepartmentId());
            meetingDetails1.setDeReserveDepartment(dept.getDeptName());
            /*TODO:调用接口：根据预订人id查询出预定人的姓名，并设置到MeetingDetails对象中*/
            SimpleUser userById = (SimpleUser)userServiceImpl.getUserById(meetingDetails1.getDeReserveId()).getData();
            meetingDetails1.setDeReserve(userById.getTrueName());
        }

        //TODO:调用接口参数当前操作需要的权限，得到true或false
        flag = CheckPermission.checkPermission("reservation:view:other");
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


    //根据详情id查询会议详情
    @Override
    public Map findMeetingDetails(Integer id) {
        Map detailsMap = new HashMap<String,Object>();
        Result result = new Result();
        if(id > 0){
            //当传递
            MeetingDetails meetingDetails = meetingDetailsMapperImpl.selectByPrimaryKey(id);
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
    public Map alterMeetingDetails(MeetingDetails meetingDetails,PubTemplate pubTemplate) {
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
            mDetails.setId(meetingDetails.getId());
            List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.byExcludeDetailsIdselectMeetingDetails(mDetails);
            //判断修改的会议时间跟以有的会议时间有没有冲突
            
            if(meetingDetailsAll.size()>0){
                for (MeetingDetails md:meetingDetailsAll){
                	//如果查到了会议则按个比较会议时间有没有冲突
                    if(md.getDeMeetingStart().before(stratTime)){  //判断已有会议开始时间，是否在修改会议开始时间之前
                        if(md.getDeMeetingOver().before(stratTime)){  //判断已有会议结束时间，是否在修改会议开始时间之前
                            flag=true;
                        }else {
                            flag=false;
                            break;
                        }
                    }else {
                        if(md.getDeMeetingStart().after(overTime)){  //判断已有会议开始时间，是否在修改会议结束时间之后
                            flag = true;
                        }else {
                            flag = false;
                            break;
                        }
                    }
                }
            }else{
            	//没有查到指定条件下有会议，所以可以直接修改
                flag = true;
            }

            if (pubTemplate != null){
                String jsonTemplate = JSON.toJSONString(pubTemplate); //将json对象转为json字符串
                meetingDetails.setDePubTemplate(jsonTemplate);
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
                meetingDetails.setDeDepartmentReservePersonId(deptReservePersonId);  //将部门预定人的id添加到meetingDetails对象中
                //时间没有冲突可以修改
                int i = meetingDetailsMapperImpl.updateByPrimaryKeySelective(meetingDetails);
                if(i>0){
                    result.setStatus(200);
                    MeetingDetails meetingDetails1 = meetingDetailsMapperImpl.selectByPrimaryKey(meetingDetails.getId());
                    //将会议开始和结束时间转为指定格式的时间
                    meetingDetails1.setStart_date(meetingUtils.transformTimeFormat(meetingDetails1.getDeMeetingStart()));
                    meetingDetails1.setEnd_date(meetingUtils.transformTimeFormat(meetingDetails1.getDeMeetingOver()));
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
                    map.put("meetingDetails",meetingDetails1);
                    return map;
                }else{
                    result.setStatus(400);
                    result.setMessage("修改会议时间与已有会议时间冲突！");
                    map.put("result",result);
                    return map;
                }

            }else {
                //时间冲突
                result.setMessage("修改会议时间与已有会议时间冲突！");
                result.setStatus(400);
                map.put("result",result);
                return map;
            }
        }else{
            //没有这个预定人
            result.setMessage("修改会议失败，无此预定人！");
            result.setStatus(400);
            map.put("result",result);
            return map;
        }

    }

    //删除会议详情（取消预约）
    @Override
    public Result deleteMeetingDetails(Integer id) {
        int i = meetingDetailsMapperImpl.deleteByPrimaryKey(id);
        Result result = new Result();
        if(i>0){
            result.setStatus(200);
        }else{
            result.setStatus(400);
        }
        return result;
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
    public JSONObject addMeetingDetails(MeetingDetails meetingDetails,PubTemplate pubTemplate) {
        JSONObject jsonObject = new JSONObject();
        String deptReservePersonId = null; //部门预定人的id
        Result result = new Result();
        MeetingDetails mDetails =  new MeetingDetails();
        int roomId = meetingDetails.getDeRoomId();
        int deReserveId = meetingDetails.getDeReserveId();

        boolean flag = false;
        Date meetingStart = meetingDetails.getDeMeetingStart(); //添加新会议的开始时间
        Date meetingOver = meetingDetails.getDeMeetingOver();  //添加新会议的结束时间

        //TODO:调用接口根据部门id已及角色id查询出部门预订人的id（一个部门可以有多个部门预定人）
        List<User> users = userServiceImpl.selectUserByDeptIdAndRoleId(meetingDetails.getDeReserveDepartmentId(), 3);
        int count =0;
        for(User user:users){
            if(count == 0){
                //取出第i个部门预定人的id
                deptReservePersonId = user.getUserId()+"";
            }else{
                deptReservePersonId = deptReservePersonId+","+user.getUserId();
            }
            count++;
        }
        System.err.println(deptReservePersonId);
        meetingDetails.setDeDepartmentReservePersonId(deptReservePersonId);

        //查询出指定日期指定会议室的会议详情
        Date startTime = meetingUtils.getStartTime(meetingStart); //获得指定日期的开始时间
        Date endTime = meetingUtils.getEndTime(meetingOver);  //获得指定日期的结束时间
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        mDetails.setDeRoomId(roomId);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);
        //判断指定日期指定的会议室已有会议有没有跟新添加会议的时间冲突
            //例12:01与12：05比较得出12：01在12:05之前，12:05在12:01之后
        if(meetingDetailsAll.size()>0){
            for (MeetingDetails md:meetingDetailsAll){
                if(md.getDeMeetingStart().before(meetingStart)){  //判断已有会议开始时间，是否在修改会议开始时间之前
                    if(md.getDeMeetingOver().before(meetingStart)){  //判断已有会议结束时间，是否在修改会议开始时间之前
                        flag=true;
                    }else {
                        flag=false;
                        break;
                    }
                }else {
                    if(md.getDeMeetingStart().after(meetingOver)){  //判断已有会议开始时间，是否在修改会议结束时间之后
                        flag = true;
                    }else {
                        flag = false;
                        break;
                    }
                }
            }
        }else{
            //当天没有冲突的会议
            flag = true;
        }

        if(pubTemplate != null){
            String jsonTemplate = JSON.toJSONString(pubTemplate); //将对象转为json字符串
            /*JSONArray.toJSONString()*/
            meetingDetails.setDePubTemplate(jsonTemplate);
        }

        if(flag){
            //没有冲突,添加会议
            if(deReserveId > 0){
                int update = meetingDetailsMapperImpl.insertSelective(meetingDetails);
                if(update == 1){
                    Integer detailsId = meetingDetailsMapperImpl.selectNewestDetailsId();//获取最新插入的会议详情的id
                    MeetingDetails meetingDetails1 = meetingDetailsMapperImpl.selectByPrimaryKey(detailsId);
                    //将会议开始和结束时间转为指定格式的时间
                    meetingDetails1.setStart_date(meetingUtils.transformTimeFormat(meetingDetails1.getDeMeetingStart()));
                    meetingDetails1.setEnd_date(meetingUtils.transformTimeFormat(meetingDetails1.getDeMeetingOver()));
                    result.setStatus(200);
                    jsonObject.put("meetingDetails",meetingDetails1);
                    jsonObject.put("result",result);
                    return jsonObject;
                }else {
                    result.setMessage("新添加会议跟已有会议时间冲突！");
                    result.setStatus(400);
                    jsonObject.put("result",result);
                    return jsonObject;
                }
            }else{
                result.setMessage("新添加会议跟已有会议时间冲突！");
                result.setStatus(400);
                jsonObject.put("result",result);
                return jsonObject;
            }

        }else{
            result.setMessage("新添加会议跟已有会议时间冲突！");
            result.setStatus(400);
            jsonObject.put("result",result);
            return jsonObject;
        }
    }

    //历史查询
    @Override
    public JSONObject selectHistory(MeetingDetails meetingDetails,Integer pageNum,Integer pageSize) {
        if(CheckPermission.checkPermission("history:view:other")){  //是否有权限查看其他历史记录
            long time = 0; //总时间
            History history = new History();
            JSONObject jsonObject = new JSONObject();
            PageHelper.startPage(pageNum,pageSize); //开启分页并设置分页条件(默认显示第几页，每页显示多少条数据)
            List<MeetingDetails> historys = meetingDetailsMapperImpl.selectHistory(meetingDetails);
            for(MeetingDetails his:historys){
                //将会议开始和结束时间转为指定格式的时间
                his.setStart_date(meetingUtils.transformTimeFormat(his.getDeMeetingStart()));
                his.setEnd_date(meetingUtils.transformTimeFormat(his.getDeMeetingOver()));
            }
            PageInfo<MeetingDetails> page = new PageInfo<>(historys);//吧查询到对象封装到PageInfo中
            for(MeetingDetails his:historys){
                //根据预定人id查询出预定人的姓名，并设置到his对象中
                System.out.println(his.getDeReserveId());
                SimpleUser userById = (SimpleUser)userServiceImpl.getUserById(his.getDeReserveId()).getData();
                his.setDeReserve(userById.getTrueName());
                his.getDeReserveDepartmentId();
                //根据部门id查询部门的姓名，并设置到his对象中
                Dept dept = deptServiceImpl.getDeptById(his.getDeReserveDepartmentId());
                his.setDeReserveDepartment(dept.getDeptName());
                //根据部门预定人的id查询出部门预定人的姓名（）
                String deDepartmentReservePersonId = his.getDeDepartmentReservePersonId();
                String[] split = deDepartmentReservePersonId.split(",");  //将部门预定人的id分割
                System.out.println(split.length);
                String [] departmentReservePerson = new String [split.length];
                for(int i = 0;i<split.length;i++){
                    int departmentReservePersonId = Integer.parseInt(split[i]);
                    //TODO:调用方法：根据部门预定人的id查询出部门预定人的名称
                    SimpleUser user = (SimpleUser)userServiceImpl.getUserById(departmentReservePersonId).getData();
                    
                    departmentReservePerson[i]=user.getTrueName(); 
                }
                his.setDeDepartmentReservePerson(departmentReservePerson);

                //将查询到的所有会议的用时时间记录起来
                time += (his.getDeMeetingOver().getTime() - his.getDeMeetingStart().getTime());
            }
            //计算用时多少天多少时多少分
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            //天
            long day = time / nd;
            //时
            long hour = time % nd / nh;
            //分
            long minute = time % nd % nh / nm;
            String deDateCount = day + "天" + hour + "小时" + minute + "分"; //用时共计

            history.setDeDateCount(deDateCount); //用时共计
            history.setPage(page);//分页记录
            history.setPages(page.getPages());//总页数
            history.setTotal(page.getTotal());//总记录数
            history.setDeMeetingCount(page.getTotal());//场次共计

            jsonObject.put("history",history);
            return jsonObject;
        }else {
            //TODO:获得当前用户Id
            SystemResult currentUser = userServiceImpl.getCurrentUser();
            SimpleUser simpleUser = (SimpleUser)currentUser.getData();
            int deReserveId = simpleUser.getUserId();
            meetingDetails.setDeReserveId(deReserveId);

            long time = 0; //总时间
            History history = new History();
            JSONObject jsonObject = new JSONObject();
            PageHelper.startPage(pageNum,pageSize); //开启分页并设置分页条件(默认显示第几页，每页显示多少条数据)
            List<MeetingDetails> historys = meetingDetailsMapperImpl.selectHistory(meetingDetails);

            PageInfo<MeetingDetails> page = new PageInfo<>(historys);//吧查询到对象封装到PageInfo中
            for(MeetingDetails his:historys){
                //根据预定人id查询出预定人的姓名，并设置到his对象中
                System.out.println(his.getDeReserveId());
                SimpleUser userById = (SimpleUser)userServiceImpl.getUserById(his.getDeReserveId()).getData();
                his.setDeReserve(userById.getTrueName());
                his.getDeReserveDepartmentId();
                //根据部门id查询部门的姓名，并设置到his对象中
                Dept dept = deptServiceImpl.getDeptById(his.getDeReserveDepartmentId());
                his.setDeReserveDepartment(dept.getDeptName());
                //根据部门预定人的id查询出部门预定人的姓名（）
                String deDepartmentReservePersonId = his.getDeDepartmentReservePersonId();
                String[] split = deDepartmentReservePersonId.split(",");  //将部门预定人的id分割
                System.out.println(split.length);
                String [] departmentReservePerson = new String [split.length];
                for(int i = 0;split.length>i;i++){
                    int departmentReservePersonId = Integer.parseInt(split[i]);
                    //TODO:调用方法：根据部门预定人的id查询出部门预定人的名称
                    SimpleUser user = (SimpleUser)userServiceImpl.getUserById(departmentReservePersonId).getData();
                    departmentReservePerson[i]=user.getTrueName();
                }
                his.setDeDepartmentReservePerson(departmentReservePerson);

                //将查询到的所有会议的用时时间记录起来
                time += (his.getDeMeetingOver().getTime() - his.getDeMeetingStart().getTime());
            }
            //计算用时多少天多少时多少分
            long nd = 1000 * 24 * 60 * 60;
            long nh = 1000 * 60 * 60;
            long nm = 1000 * 60;
            //天
            long day = time / nd;
            //时
            long hour = time % nd / nh;
            //分
            long minute = time % nd % nh / nm;
            String deDateCount = day + "天" + hour + "小时" + minute + "分"; //用时共计

            history.setDeDateCount(deDateCount); //用时共计
            history.setPage(page);//分页记录
            history.setPages(page.getPages());//总页数
            history.setTotal(page.getTotal());//总记录数
            history.setDeMeetingCount(page.getTotal());//场次共计

            jsonObject.put("history",history);
            return jsonObject;
        }

    }

    //删除pub模板
    @Override
    public Map deletePubTemplate(Integer id,String pubTemplate) {
        Result result = new Result();
        Map<String,Object> map = new HashMap<String,Object>();
        int delete = meetingDetailsMapperImpl.deletePubTemplate(id, pubTemplate);
        if(delete > 0){
            result.setStatus(200);
            map.put("result",result);
            MeetingDetails meetingDetails = meetingDetailsMapperImpl.selectByPrimaryKey(id);
            String dePubTemplate = meetingDetails.getDePubTemplate();
            map.put("pubTemplate",dePubTemplate);
        }else {
            result.setStatus(400);
            map.put("result",result);
        }
        return map;
    }




    //加载pub模板（）
    @Override
    public void loadPubTemplateCon() {
        boolean flag = false;
        //遍历序号
        //查询出当天所有会议
        MeetingDetails mDetails =  new MeetingDetails();  //创建一个查询条件体
        Date currentTime = new Date();//当前时间
        Date startTime = meetingUtils.getStartTime(currentTime);   //获得当天的开始时间
        Date endTime = meetingUtils.getEndTime(currentTime);  //获得当天的结束时间
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetails(mDetails);

        //对时间进行格式化
        DateFormat b=new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
        //会议个数
        int meetingCount = meetingDetailsAll.size();
        //遍历所有会议，取出会议的开时间跟当前时间比较，如果相等则调用加载pub模板的aqi
        for(MeetingDetails me:meetingDetailsAll){
            //第i个会议详情
            Date meetingStartTime = me.getDeMeetingStart();
            try {
                currentTime = b.parse(b.format(currentTime));  //当前时间
                meetingStartTime = b.parse(b.format(meetingStartTime)); //第i个会议开始的时间
            } catch (ParseException e) {
                e.printStackTrace();
            }
            /*TODO：调用接口获取预设模板提前开启的时间,存储的时间为整分且以毫秒的形式存储，例只可以选择1分钟，3分钟存储到库中就是60000毫秒，180000毫秒*/
            long time = 300000;//300000相当于5分钟
            /*TODO：调用接口获取当前会议室有没有人，如果有人则不加载预设模板，无人则调用*/
            if(meetingStartTime.getTime()-time == currentTime.getTime()){
            /*判断当前是否是无模板，如果是则不去调用加载pub模板api*/
                String dePubTemplate1 = me.getDePubTemplate();
                if("{\"Content\":\"\",\"LayoutId\":-1,\"Thumb\":\"https://gss3.bdstatic.com/-Po3dSag_xI4khGkpoWK1HF6hhy/baike/w%3D268%3Bg%3D0/sign=69fb9c56cb95d143da76e3254bcbe53f/d1a20cf431adcbef5d550e53afaf2edda3cc9f05.jpg\",\"Id\":-1,\"Name\":\"nullTemplate\"}".equals(dePubTemplate1)){
                    continue;
                }
                //调用pub加载pub模板的aqi
                    //获取pubIp
                Integer roomId = me.getDeRoomId();
                String pubIp = RoomMapperImpl.selectByPrimaryKey(roomId).getPubIp();
                //获取模板id
                String dePubTemplate = me.getDePubTemplate();  //返回一个json格式的字符串
                PubTemplate pubTemplate = JSON.parseObject(dePubTemplate, PubTemplate.class);//将json字符串转为对应的对象
                //调用pub加载pub模板的aqi（调用api就使用restTemplate.getForEntity（"访问的url地址,访问的方法返回类型.class"）.getBody();）
                restTemplate.getForEntity("http://"+pubIp+"/ajax/presetmode/load?Id="+pubTemplate.getId(), String.class).getBody();
                flag=true;
            }
        }

        //如果执行到这里说明程序暂停1分钟，
        if(flag){
            try {
                Thread.sleep(60000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }


    //根据预定人姓名查询预定人ID
    @Override
    public int findReserveId(String reserveName) {
        return meetingDetailsMapperImpl.selectReserveId(reserveName);
    }


    //查看会议当前模板
    @Override
    public JSONObject findMeetingPubTemplate(int id) {
        JSONObject jsonObject = new JSONObject();
        Result result = new Result();
        MeetingDetails meetingDetails = meetingDetailsMapperImpl.selectByPrimaryKey(id);
        if(meetingDetails != null){
            String dePubTemplate = meetingDetails.getDePubTemplate();
            result.setStatus(200);
            jsonObject.put("result",result);
            PubTemplate pubTemplate = jsonObject.parseObject(dePubTemplate, PubTemplate.class);
            jsonObject.put("pubTemplate",pubTemplate);
            return jsonObject;
        }else{
            result.setStatus(400);
            return jsonObject;
        }
    }

    //保存模板
    @Override
    public Map saveTemplate(MeetingDetails meetingDetails,PubTemplate  pubTemplate) {
        Result result = new Result();
        //将对象转为json字符串
        String jsonTemplate = JSON.toJSONString(pubTemplate);
        return alterMeetingDetails(meetingDetails, pubTemplate);
    }

    //根据时间和设备列表查询相应的会议详情
    @Override
    public JSONObject byEquipmentListSelectMeetingDetails(String[] equipmentList,Date todayTime) {
        Result result = new Result();
        int flag = 0;
        JSONObject jsonObject = new JSONObject();
        List<Room> roomAll = RoomMapperImpl.selectRoomAll();
        boolean falg1 = true;
        List<Integer> excludeRoom = new ArrayList<>() ;//不包含所选全部设备的会议室
        if(equipmentList != null){
            for(Room room:roomAll){
                //遍历所有会议室
                for(int i = 0;equipmentList.length>i;i++){
                    //遍历所有所选设备
                    if(room.getMeetingEquipmentList().indexOf(equipmentList[i]) == -1){
                        //当前会议室不包含某一个所选设备，所以跳出本次循环,并将不符合条件的会议室记录下来
                        falg1 = false;
                        excludeRoom.add(room.getMeetingRoomId());
                        break;
                    }
                }
            }
        }
        MeetingTime meetingTime = new MeetingTime();
        //调用接口参数当前操作需要的权限，得到true或false
            /*Power = 调用是否有权限的接口*/
        //TODO:调用接口获得当前用户的id
        SystemResult currentUser = userServiceImpl.getCurrentUser();
        SimpleUser user = (SimpleUser)currentUser.getData();
        int deReserveId = user.getUserId();
        Boolean Power = true; //是否有查看其它人会议详情的权限
        //查询出当天符合设备条件的会议室对应的所有会议
        MeetingDetails mDetails =  new MeetingDetails();  //创建查询体
        Date startTime = meetingUtils.getStartTime(todayTime);
        Date endTime = meetingUtils.getEndTime(todayTime);
        mDetails.setDeMeetingStart(startTime);
        mDetails.setDeMeetingOver(endTime);
        List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.byEquipmentListSelectMeetingDetails(startTime,endTime,excludeRoom);

        for(MeetingDetails meetingDetails:meetingDetailsAll){
            //将会议开始和结束时间转为指定格式的时间
            meetingDetails.setStart_date(meetingUtils.transformTimeFormat(meetingDetails.getDeMeetingStart()));
            meetingDetails.setEnd_date(meetingUtils.transformTimeFormat(meetingDetails.getDeMeetingOver()));
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

        jsonObject.put("meetingTime",meetingTime);
        jsonObject.put("roomAll",roomAll);

        if(Power){
            //可以查看所有会议详情
            for(MeetingDetails meetingDetails:meetingDetailsAll){
                meetingDetails.setDeShow(1);
            }

            jsonObject.put("meetingDetailsAll",meetingDetailsAll);

            result.setStatus(200);
            jsonObject.put("result",result);
            return jsonObject;
        }else{
            //不可查看所有会议详情
            for(MeetingDetails meetingDetails:meetingDetailsAll){
                if(meetingDetails.getDeReserveId()==deReserveId){
                    meetingDetails.setDeShow(1);
                }
            }
            jsonObject.put("meetingDetailsAll",meetingDetailsAll);
            result.setStatus(200);
            jsonObject.put("result",result);
            return jsonObject;
        }

    }

    //根据预定人Id查询当天预订人所预定的会议
    @Override
    public JSONObject byDeReserveIdSelectMeetingDetails(Integer deReserveId) {
        JSONObject jsonObject = new JSONObject();
        Date currentTime = new Date();
        Result result = new Result();
        Date startTime = meetingUtils.getStartTime(currentTime);
        Date endTime = meetingUtils.getEndTime(currentTime);
        List<MeetingDetails> meetingDetails = meetingDetailsMapperImpl.byDeReserveIdSelectMeetingDetails(deReserveId, startTime, endTime);
        if (meetingDetails.size() > 0){
            result.setStatus(200);
        }else {
            result.setStatus(400);
        }
        jsonObject.put("meetingDetailsList",meetingDetails);
        jsonObject.put("result",result);
        return jsonObject;
    }


}
