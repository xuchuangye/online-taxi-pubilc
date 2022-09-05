package com.mashibing.servicedriveruser;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xcy
 * @date 2022/9/5 - 9:33
 */
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.mashibing.servicedriveruser.mapper")
public class ServiceDriverUserApplication {

	public static void main(String[] args) {
		SpringApplication.run(ServiceDriverUserApplication.class, args);
	}
}
