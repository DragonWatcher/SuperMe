package com.ahav.reserve;


import com.ahav.reserve.mapper.MeetingDetailsMapper;
import com.ahav.reserve.mapper.RoomMapper;
import com.ahav.reserve.pojo.MeetingDetails;
import com.ahav.reserve.pojo.Room;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ReserveApplicationTests {
	@Autowired
	private MeetingDetailsMapper meetingDetailsMapperImpl;
	@Autowired
	private RoomMapper RoomMapperImpl;
	@Test
	public void contextLoads() {
		JSONObject jsonObject = new JSONObject();
		List list = new ArrayList();
		String string = "{\"LayoutId\":6,\"Thumb\":\"/images/preset/2018_06_04_13_30_06.png\",\"Id\":11,\"Name\":\"2018-06-04\"}";
		List<MeetingDetails> meetingDetailsAll = meetingDetailsMapperImpl.selectMeetingDetailsAll();
		List<Room> rooms = RoomMapperImpl.selectRoomAll();
		list.add(meetingDetailsAll);
		list.add(rooms);
		jsonObject.put("data",list);
		System.out.println(jsonObject);

	}

}
