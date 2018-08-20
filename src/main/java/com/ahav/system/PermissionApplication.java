package com.ahav.system;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.ahav.system.dao")//扫描 mybatis 包
public class PermissionApplication {
    private static final Logger logger = LoggerFactory.getLogger(PermissionApplication.class);
	
	public static void main(String[] args){
		SpringApplication.run(PermissionApplication.class, args);
		logger.info("======================启动完成=====================");
	}
	
}
