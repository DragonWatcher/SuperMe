package com.ahav.system.util;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;

public class CheckPermission {

	public static boolean checkPermission(String permission){
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.checkPermission(permission);
			return true;
		} catch (AuthorizationException e) {
			return false;
		}
//		return true;
	}
}
