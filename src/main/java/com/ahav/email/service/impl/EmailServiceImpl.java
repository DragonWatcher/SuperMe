package com.ahav.email.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ahav.email.mapper.EmailMapper;
import com.ahav.email.pojo.Email;
import com.ahav.email.pojo.ReceiveMail;
import com.ahav.email.service.EmailService;
import com.ahav.email.util.EmailUtils;
import com.alibaba.fastjson.JSONObject;


@Service
@Transactional
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailMapper emailMapper;

    @Override
    public JSONObject insertEmail(Email record) {
        Email email = emailMapper.selectEmail();
        JSONObject jo = new JSONObject();
        if (email == null) {
            boolean result = emailMapper.insert(record);
            if (result == true) {
                jo.put("code", HttpStatus.OK.value());
                jo.put("result", result);
            } else {
                jo.put("code", HttpStatus.BAD_REQUEST.value());
                jo.put("result", result);
            }
        } else {
            jo.put("code", HttpStatus.BAD_REQUEST.value());
        }
        return jo;
    }

    @Override
    public JSONObject getEmail() {
        JSONObject json = new JSONObject();
        Email email = emailMapper.selectEmail();
        if (email != null) {
            json.put("code", HttpStatus.OK.value());
            json.put("result", email);
        } else {
            json.put("code", HttpStatus.BAD_REQUEST.value());
        }
        return json;
    }

    @Override
    public JSONObject editEmail(Email record) {
        JSONObject json = new JSONObject();
        boolean result = emailMapper.updateEmail(record);
        if (result == true) {
            json.put("code", HttpStatus.OK.value());
            json.put("result", result);
        } else {
            json.put("code", HttpStatus.BAD_REQUEST.value());
            json.put("result", result);
        }
        return json;
    }

    @Override
    public JSONObject sendEmail(ReceiveMail receiver) {
        JSONObject json = new JSONObject();
        Email sendemail = emailMapper.selectEmail();
        if (sendemail != null) {
            boolean result = EmailUtils.sendMail(sendemail, receiver);
            if (result == true) {
                json.put("code", HttpStatus.OK.value());
                json.put("result", result);
            } else {
                json.put("code", HttpStatus.BAD_REQUEST.value());
                json.put("result", result);
            }
        } else {
            json.put("code", HttpStatus.BAD_REQUEST.value());
        }
        return json;
    }
}
