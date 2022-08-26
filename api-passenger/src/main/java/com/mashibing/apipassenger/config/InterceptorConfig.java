package com.mashibing.apipassenger.config;

import com.mashibing.apipassenger.interceptor.JwtInterceptor;
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
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new JwtInterceptor())
				//所有拦截的请求
				.addPathPatterns("/**")
				//排除的拦截请求
				.excludePathPatterns("/noauthTest")
				//乘客获取验证码不需要拦截
				.excludePathPatterns("/verification-code")
				//乘客校验验证码不需要拦截
				.excludePathPatterns("/verification-code-check");
	}
}
