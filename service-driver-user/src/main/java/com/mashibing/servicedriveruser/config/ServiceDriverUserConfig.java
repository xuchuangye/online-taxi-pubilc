package com.mashibing.servicedriveruser.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2022/9/5 - 9:41
 */
@Configuration
public class ServiceDriverUserConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
