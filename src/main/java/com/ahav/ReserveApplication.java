package com.ahav;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@MapperScan("com.ahav.reserve.mapper,com.ahav.system.dao")//扫描 mybatis 包
public class ReserveApplication {
    private static final Logger logger = LoggerFactory.getLogger(ReserveApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(ReserveApplication.class, args);
		logger.info("======================启动完成=====================");
	}
	@Autowired
	private RestTemplateBuilder builder;

	// 使用
	//
	// RestTemplateBuilder来实例化RestTemplate对象，spring默认已经注入了RestTemplateBuilder实例
	@Bean
	public RestTemplate restTemplate() {
		return builder.build();
	}

/*	@Bean
	public DeptService getDeptService(){
		return new DeptServiceImpl();
	}
	@Bean
	public DeptDao getDeptDao(){
		return new DeptDao() {
			@Override
			public List<Dept> allDepts() {
				return null;
			}

			@Override
			public void delDeptById(Integer integer) {

			}

			@Override
			public void insertDept(Dept dept) {

			}

			@Override
			public void updateDept(Dept dept) {

			}

			@Override
			public Dept getDeptById(Integer integer) {
				return null;
			}
		};
	}*/

}
