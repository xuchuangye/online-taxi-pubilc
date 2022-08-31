package com.mashibing.serviceprice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author xcy
 * @date 2022/8/30 - 17:02
 */
@SpringBootApplication
@EnableDiscoveryClient
public class ForecastPriceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ForecastPriceApplication.class, args);
	}
}
