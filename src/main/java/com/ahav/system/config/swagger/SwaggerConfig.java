package com.ahav.system.config.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Swagger2配置类
 * 在与spring boot集成时，放在与Application.java同级的目录下。
 * 通过@Configuration注解，让Spring来加载该类配置。
 * 再通过@EnableSwagger2注解来启用Swagger2。
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    /**
     * 创建API应用
     * apiInfo() 增加API相关信息
     * 通过select()函数返回一个ApiSelectorBuilder实例,用来控制哪些接口暴露给Swagger来展现，
     * 本例采用指定扫描的包路径来定义指定要建立API的目录。
     *
     * @return
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ahav"))//扫描com路径下的api文档
                .paths(PathSelectors.any())//路径判断
                .build();
    }

    /**
     * 创建该API的基本信息（这些基本信息会展现在文档页面中）
     * 访问地址：http://项目实际地址/swagger-ui.html
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("springboot利用swagger构建api文档")//标题
                .description(
                        "王晓辉负责的模块有：菜单(menu-contorller)、角色(role-controller)、权限(permission-controller); \n 牟昊天负责的模块有：用户(user-contorller)、部门(dept-controller)")//描述
                .termsOfServiceUrl("http://www.google.com.hk")//（不可见）条款地址
                .contact("王晓辉、孟凡营、牟昊天")//作者信息
                .version("1.0")//版本号
                .build();
    }
}
