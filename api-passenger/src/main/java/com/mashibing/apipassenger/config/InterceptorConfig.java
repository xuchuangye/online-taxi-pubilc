package com.mashibing.apipassenger.config;

import com.mashibing.apipassenger.interceptor.JwtInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器的配置类
 *
 * @author xcy
 * @date 2022/8/26 - 10:32
 */
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
	/**
	 * 拦截器提前进行初始化
	 *
	 * @return 返回拦截器对象
	 */
	@Bean
	public JwtInterceptor jwtInterceptor() {
		return new JwtInterceptor();
	}

	/**
	 * 添加拦截器，并且配置拦截请求的规则
	 *
	 * @param registry 拦截器注册表
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(jwtInterceptor())
				//所有拦截的请求
				.addPathPatterns("/**")
				//排除的拦截请求
				.excludePathPatterns("/noauthTest")
				//乘客获取验证码不需要拦截
				.excludePathPatterns("/verification-code")
				//乘客校验验证码不需要拦截
				.excludePathPatterns("/verification-code-check")
				//token-refresh请求如果被拦截，那么还需要accessToken，并且accessToken过期了
				//那么该请求就处于很尴尬的地位
				//而我们需要的是使用refreshToken创建accessToken，并且刷新refreshToken
				//所以排除token-refresh请求
				.excludePathPatterns("/token-refresh");
	}
}
