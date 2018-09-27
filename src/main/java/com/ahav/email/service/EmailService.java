package com.ahav.email.service;

        import com.ahav.email.pojo.Email;
        import com.ahav.email.pojo.ReceiveMail;
        import com.alibaba.fastjson.JSONObject;

public interface EmailService {
    public JSONObject insertEmail(Email record);
    public JSONObject getEmail();
    public JSONObject editEmail(Email record);
    public JSONObject sendEmail(ReceiveMail receiver);

}
