package com.mashibing.apidriver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xcy
 * @date 2022/9/6 - 8:51
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
public class ApiDriverApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiDriverApplication.class, args);
	}
}
