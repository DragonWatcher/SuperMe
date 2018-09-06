package com.ahav.system.config.common;

import javax.servlet.MultipartConfigElement;

import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
public class CommonConfiguration extends WebMvcConfigurerAdapter {

    /**
     * 文件上传配置，在application配置文件中设置不起作用！
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        // 单个文件最大
        factory.setMaxFileSize("10240KB"); // KB,MB
        // 设置总上传数据总大小
        factory.setMaxRequestSize("102400KB");
        return factory.createMultipartConfig();
    }
    
//    @Bean
//    public CorsConfiguration buildConfig() {
//        CorsConfiguration corsConfiguration = new CorsConfiguration();
//        corsConfiguration.addAllowedOrigin("*"); // 1
//        corsConfiguration.addAllowedHeader("*"); // 2
//        corsConfiguration.addAllowedMethod("*"); // 3
//        return corsConfiguration;
//    }
//    
//    @Bean
//    public CorsFilter corsFilter() {
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", buildConfig()); // 4
//        return new CorsFilter(source);
//    }
    
//    @Override
//    public void addCorsMappings(CorsRegistry registry) {
//        registry.addMapping("/**")
//            .allowedOrigins("*")
//            .allowedMethods("*")
//            .allowedHeaders("*")
//            .maxAge(3600)
//            .allowCredentials(true);
//    }

//    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
//            throws IOException, ServletException {
//        HttpServletResponse response = (HttpServletResponse).k res;
//        HttpServletRequest reqs = (HttpServletRequest) req;
//
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        response.setHeader("Access-Control-Allow-Credentials", "true");
//        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
//        response.setHeader("Access-Control-Max-Age", "3600");
//        response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
//        chain.doFilter(reqs, response);
//    }
}
