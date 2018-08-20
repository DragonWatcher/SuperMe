package com.ahav.system.service.impl;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ahav.system.entity.Result;
import com.ahav.system.entity.SimpleUser;
import com.ahav.system.entity.User;
import com.ahav.system.service.LoginService;


/**
 * LoginService实现类
 * <br>类名：LoginServiceImpl<br>
 * 作者： mht<br>
 * 日期： 2018年8月5日-下午7:10:32<br>
 */
@Service
public class LoginServiceImpl implements LoginService{
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public Result login(String username, String password) {
        Result loginResult = null;
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 通过shiro获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 登录提交
            currentUser.login(token);
            // 封装登录结果
            loginResult = new Result(HttpStatus.OK.value(), "登录成功！",
                    new SimpleUser((User) SecurityUtils.getSubject().getPrincipal()));
        } catch (AuthenticationException e) {
            logger.info("用户名或密码错误>>>username:{},password:{}", username, password);
            loginResult = new Result(HttpStatus.FORBIDDEN.value(), "用户名或密码错误，登录失败！", null);
        }
        logger.info("用户登录成功>>>username:{}", username);

        return loginResult;
    }

    @Override
    public Result logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();
        
        return new Result(HttpStatus.OK.value(), "用户已登出！", null);
    }

}
