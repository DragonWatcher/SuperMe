package com.ahav.system.service.impl;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ahav.system.entity.SimpleUser;
import com.ahav.system.entity.SystemResult;
import com.ahav.system.entity.User;
import com.ahav.system.service.LoginService;
import com.ahav.system.util.SystemConstant;
import com.netease.domainmail.RSATool;

/**
 * LoginService实现类 <br>
 * 类名：LoginServiceImpl<br>
 * 作者： mht<br>
 * 日期： 2018年8月5日-下午7:10:32<br>
 */
@Service
public class LoginServiceImpl implements LoginService {
    private static final Logger logger = LoggerFactory.getLogger(LoginServiceImpl.class);

    @Override
    public SystemResult login(String username, String password) {
        SystemResult loginResult = null;
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 通过shiro获取当前用户
        Subject currentUser = SecurityUtils.getSubject();
        try {
            // 登录提交
            currentUser.login(token);

            // 封装登录结果
            loginResult = new SystemResult(HttpStatus.OK.value(), "登录成功！",
                    new SimpleUser((User) SecurityUtils.getSubject().getPrincipal()));
        } catch (AuthenticationException e) {
            e.printStackTrace();
            logger.info("用户名或密码错误>>>username:{},password:{}", username, password);
            loginResult = new SystemResult(HttpStatus.FORBIDDEN.value(), "用户名或密码错误，登录失败！", null);
        }
        logger.info("用户登录成功>>>username:{}", username);

        return loginResult;
    }

    @Override
    public SystemResult logout() {
        Subject currentUser = SecurityUtils.getSubject();
        currentUser.logout();

        return new SystemResult(HttpStatus.OK.value(), "用户已登出！", null);
    }

    @Override
    public void ntesLogin(String username, HttpServletResponse response) {
        // 测试账号
        String account_name = SystemConstant.DEFAULT_ACCOUNT;

        // 当前时间戳
        long currTime = System.currentTimeMillis();

        // 安恒网易企业邮箱域名，为啥域名登录前后会变化？mail.ahav.com.cn/owa/
        String domain = SystemConstant.AHAV_DOMAIN;

        // 私钥
        String priKey = SystemConstant.PRI_KEY;

        // 要加密的信息
        String src = account_name + domain + currTime;

        RSATool rsa = new RSATool();
        // 加密串 (摘要)
        String enc = rsa.generateSHA1withRSASigature(src, priKey);

        // 提交登录的url,后台加上必须的参数,为了安全，可使用https提交
        String url = "https://entryhz.qiye.163.com/domain/oa/Entry?domain=" + domain + "&account_name=" + account_name
                + "&time=" + currTime + "&enc=" + enc + "&language=0";

        // 登录,也可以采用form表单post提交的方式。
        try {
            response.sendRedirect(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
