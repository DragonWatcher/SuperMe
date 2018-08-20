package com.ahav.system.config.shiro;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.SecurityManager;//注意：这个类需要手动导入，不然会报错
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {

	//注入ShiroFilter
	@Bean
	public ShiroFilterFactoryBean shiroFilterFactory(SecurityManager securityManager) { 
		
		//1. 定义ShiroFilterFactoryBean
		ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
		shiroFilterFactoryBean.getFilters().put("authc", new ShiroFilterUtil());
		//2. 设置SecurityManager
		shiroFilterFactoryBean.setSecurityManager(securityManager);
		//3. 配置拦截器：使用map进行配置
		Map<String, String> filterChainMap = new LinkedHashMap<String, String>();//因为LinkHashMap是有序的,shiro会根据添加的顺序进行拦截
		
		//设置默认登录的url
		filterChainMap.put("/users/login", "anon");

		filterChainMap.put("/swagger-ui.html", "anon");
		filterChainMap.put("/webjars/**", "anon");
		filterChainMap.put("/v2/**", "anon");
		filterChainMap.put("/swagger-resources/**", "anon");
		
		// authc: 所有的 url 都必须通过认证才可以访问
		filterChainMap.put("/**", "authc");
		
		//配置退出过滤器 logout，这个有 shiro 实现
		filterChainMap.put("/users/logout", "logout");
		
		//设置未登录拦截处理url
		shiroFilterFactoryBean.setLoginUrl("/unauth");
		//设置登录成功跳转页面
//		shiroFilterFactoryBean.setSuccessUrl("/index");
		//未授权跳转页面
//		shiroFilterFactoryBean.setUnauthorizedUrl("/unauth");
		
		
		shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
		
		return shiroFilterFactoryBean;
	}

	/**
     * 注入ShiroRealm 
     * 不能省略，会导致Service无法注入
     */
	@Bean
	public UserRealm myShiroRealm(){
		UserRealm myRealm = new UserRealm();
//		myRealm.setCredentialsMatcher(hashedCredentialsMatcher());
		return myRealm;
	}
	
	@Bean
	public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(myShiroRealm());
//        // 自定义缓存实现 使用redis
//        securityManager.setCacheManager(cacheManager());
		return securityManager;
	}
	
    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     * ）
     *	添加用户的时候使用了md5 加密，所以这里要告诉shiro加密规则，这样在subject.login(token);的时候shiro才能以同样的加密规则加密用户输入的password
     * @return
     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(1);//散列的次数，比如散列两次，相当于 md5(md5(""));
//        return hashedCredentialsMatcher;
//    }
	
    /**
     * cacheManager 缓存 redis实现
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    @Bean
//    public RedisCacheManager cacheManager() {
//        RedisCacheManager redisCacheManager = new RedisCacheManager();
//        redisCacheManager.setRedisManager(redisManager());
//        return redisCacheManager;
//    }
	
    /**
     * 配置shiro redisManager
     * <p>
     * 使用的是shiro-redis开源插件
     *
     * @return
     */
//    public RedisManager redisManager() {
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost("127.0.0.1");
//        redisManager.setPort(6379);
//        redisManager.setExpire(1800);// 配置缓存过期时间
////        redisManager.setTimeout(timeout);
////        redisManager.setPassword(password);
//        return redisManager;
//    }
    
    /**
     * 开启shiro aop注解支持.
     * 使用代理方式;所以需要开启代码支持;
     *
     * @param securityManager
     * @return
     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
}
