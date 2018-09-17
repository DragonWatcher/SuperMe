package com.ahav.system.util;

import com.alibaba.fastjson.JSONObject;

/**
 * 简化put操作
 * <br>类名：MyJSONObject<br>
 * 作者： mht<br>
 * 日期： 2018年9月17日-上午12:33:25<br>
 */
@SuppressWarnings("serial")
public class MyJSONObject extends JSONObject{

    @Override
    public MyJSONObject put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
