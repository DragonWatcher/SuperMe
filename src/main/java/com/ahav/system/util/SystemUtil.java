package com.ahav.system.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;

public class SystemUtil {

	/**
	 * 检查用户是否登录的方法
	 * 登录成功时在登录验证的方法里把 (token,user)存入了session，
	 * 这里根据token获取user，不为空说明用户已登录
	 * @param token
	 * @return
	 */
	public static boolean checkIsLogin(String token) {
		// TODO Auto-generated method stub
		Session session = SecurityUtils.getSubject().getSession();
		Object attribute = session.getAttribute(token);
		System.out.println(attribute);
		if(attribute != null){
			return true;
		}else{
			return false;
		}
	}
}
