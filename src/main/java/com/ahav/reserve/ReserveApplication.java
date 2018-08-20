package com.ahav.reserve;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ReserveApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReserveApplication.class, args);
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
