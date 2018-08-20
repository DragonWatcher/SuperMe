package com.ahav.system.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket createRestApi(){
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.ahav.system.controller"))
				.build();
	}
	
	private ApiInfo apiInfo(){
		return new ApiInfoBuilder().title("springboot利用swagger构建api文档")
				.description("王晓辉负责的模块有：菜单(menu-contorller)、角色(role-controller)、权限(permission-controller); \n 牟昊天负责的模块有：用户(user-contorller)、部门(dept-controller)")
				.version("1.0")
				.build();
	}
	
}
