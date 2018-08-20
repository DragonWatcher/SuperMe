package com.ahav.system.config.shiro;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import com.ahav.system.entity.Permission;
import com.ahav.system.entity.Role;
import com.ahav.system.entity.User;
import com.ahav.system.service.PermissionService;
import com.ahav.system.service.UserService;
/**
 * 自定义用户Realm
 * <br>类名：UserRealm<br>
 * 作者： mht<br>
 * 日期： 2018年8月3日-下午10:44:00<br>
 */
public class UserRealm extends AuthorizingRealm {
	
	private final static Logger logger=LoggerFactory.getLogger(UserRealm.class);

    @Autowired
    private UserService userService;
    
    @Autowired
    private PermissionService perService;
    
    /** 系统内置散列迭代次数 */
    public static final int HASH_ITERATIONS = 0x5EC;

    {
        // 初始化散列的密码匹配器
        HashedCredentialsMatcher hashMatcher = new HashedCredentialsMatcher();
        hashMatcher.setHashAlgorithmName(Md5Hash.ALGORITHM_NAME);
        // TODO:置为false则加盐加密验证不起作用!!
        hashMatcher.setStoredCredentialsHexEncoded(true);
        hashMatcher.setHashIterations(HASH_ITERATIONS);
        this.setCredentialsMatcher(hashMatcher);
    }

    /**
     * subject 授权信息获取
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	logger.info("权限配置-->doGetAuthorizationInfo");
		
    	User principal = (User) SecurityUtils.getSubject().getPrincipal();
		String loginName = principal.getUsername().toString();
        if (!StringUtils.isEmpty(loginName)) {
        	SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        	logger.info("----------------------------->"+principals.getPrimaryPrincipal());
        	User user = (User) userService.findByName(loginName);
        	//一个用户有且只有一个角色
        	Role role = user.getRole();
    		if(role != null){
    			authorizationInfo.addRole(role.getRoleName());
    			List<Permission> perms = perService.findPermissionByRoleId(role.getRoleId());
    			System.out.println(role.getChinaRole() + "的权限有："+role.getPermissions());
    			if(perms != null && !perms.isEmpty()){
    				for(Permission permission : perms){
            			authorizationInfo.addStringPermission(permission.getDescription());
            		}
    			}
    		}
        	logger.info("用户"+user.getUsername()+"具有的角色:"+authorizationInfo.getRoles());
        	logger.info("用户"+user.getUsername()+"具有的权限:"+authorizationInfo.getStringPermissions());
        	return authorizationInfo;
        }
		return null;
    }

    /**
     * subject 认证信息获取
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
            throws AuthenticationException {
        // 将token转为UsernamePasswordToken，便于取得用户名及密码
        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 获取token中的用户名
        String username = userToken.getUsername();
        // 查询数据库中是否还有指定用户名的账号
        User userDB = (User) userService.getUserByName(username).getData();
        if (userDB == null) {
            throw new UnknownAccountException("账号" + username + "不存在！");
        }
        // 查询用户的角色和权限存到SimpleAuthenticationInfo中，
        // 这样在其它地方SecurityUtils.getSubject().getPrincipal()就能拿出用户的所有信息，包括角色和权限
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(userDB, userDB.getPwd(), getName());
        System.out.println("shiro调用验证登录");
        // 如果盐值不为null，则添加盐值
        if (userDB.getSalt() != null) {
            ByteSource salt = ByteSource.Util.bytes(userDB.getSalt());
            info.setCredentialsSalt(salt);
        }

        return info;
    }
}
