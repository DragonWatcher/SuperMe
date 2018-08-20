package com.ahav.system.config.shiro;
//package com.ahav.config.shiro;
//
//import org.apache.shiro.mgt.SecurityManager;
//import org.apache.shiro.realm.Realm;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
//import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
//import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
///**
// * shiro配置类
// * <br>类名：ShiroConfigeration<br>
// * 作者： mht<br>
// * 日期： 2018年8月3日-下午10:44:12<br>
// */
//@Configuration
//public class ShiroConfigeration {
//
//    @Bean
//    public Realm Realm() {
//        return new UserRealm();
//    }
//
//    /**
//     * 配置shiro拦截器，用于url，粗粒度拦截
//     * <br>作者： mht<br> 
//     * 时间：2018年8月3日-上午10:29:07<br>
//     * @return
//     */
//    @Bean
//    public ShiroFilterChainDefinition chain() {
//        DefaultShiroFilterChainDefinition chain = new DefaultShiroFilterChainDefinition();
//
//        chain.addPathDefinition("/users/login", "anon");
//        // 除了以上url剩下的都需要登录 TODO:拦截后的跳转功能
//        //swagger接口权限 开放
//        chain.addPathDefinition("/swagger-ui.html", "anon");
//        chain.addPathDefinition("/webjars/**", "anon");
//        chain.addPathDefinition("/v2/**", "anon");
//        chain.addPathDefinition("/swagger-resources/**", "anon");
//        chain.addPathDefinition("/**", "authc");
//
//        
//        return chain;
//    }
//    @Bean(name = "shiroFilter")
//    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.getFilters().put("authc", new ShiroFilterUtil());
////        //Shiro的核心安全接口,这个属性是必须的
////        shiroFilterFactoryBean.setSecurityManager(securityManager);
////        //要求登录时的链接(可根据项目的URL进行替换),非必须的属性,默认会自动寻找Web工程根目录下的"/login.jsp"页面
////        shiroFilterFactoryBean.setLoginUrl("/login");
////        //登录成功后要跳转的连接,逻辑也可以自定义，例如返回上次请求的页面
////        shiroFilterFactoryBean.setSuccessUrl("/index");
////        //用户访问未对其授权的资源时,所显示的连接
////        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
////        /*定义shiro过滤器,例如实现自定义的FormAuthenticationFilter，需要继承FormAuthenticationFilter **本例中暂不自定义实现，在下一节实现验证码的例子中体现 */
////
////        /*定义shiro过滤链 Map结构 * Map中key(xml中是指value值)的第一个'/'代表的路径是相对于HttpServletRequest.getContextPath()的值来的 * anon：它对应的过滤器里面是空的,什么都没做,这里.do和.jsp后面的*表示参数,比方说login.jsp?main这种 * authc：该过滤器下的页面必须验证后才能访问,它是Shiro内置的一个拦截器org.apache.shiro.web.filter.authc.FormAuthenticationFilter */
////        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
////        // 配置退出过滤器,其中的具体的退出代码Shiro已经替我们实现了
////        filterChainDefinitionMap.put("/logout", "logout");
////
////        // <!-- 过滤链定义，从上向下顺序执行，一般将 /**放在最为下边 -->:这是一个坑呢，一不小心代码就不好使了;
////        // <!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
////        filterChainDefinitionMap.put("/login", "anon");//anon 可以理解为不拦截
////        filterChainDefinitionMap.put("/reg", "anon");
////        filterChainDefinitionMap.put("/plugins/**", "anon");
////        filterChainDefinitionMap.put("/pages/**", "anon");
////        filterChainDefinitionMap.put("/api/**", "anon");
////        filterChainDefinitionMap.put("/dists/img/*", "anon");
////        filterChainDefinitionMap.put("/**", "authc");
////
////        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//
//        return shiroFilterFactoryBean;
//    }
//    
//}
