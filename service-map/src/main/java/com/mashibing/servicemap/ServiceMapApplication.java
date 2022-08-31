package com.mashibing.servicemap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xcy
 * @date 2022/8/31 - 9:09
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ServiceMapApplication {
	public static void main(String[] args) {
		SpringApplication.run(ServiceMapApplication.class, args);
	}
}
