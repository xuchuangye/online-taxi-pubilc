package com.mashibing.servicemap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2022/8/31 - 11:11
 */
@Configuration
public class DirectionConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
