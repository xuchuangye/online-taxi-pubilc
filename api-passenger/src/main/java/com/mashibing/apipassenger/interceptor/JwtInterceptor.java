package com.mashibing.apipassenger.interceptor;

import com.alibaba.nacos.common.utils.StringUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.mashibing.internalcommon.constant.TokenConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * Jwt拦截器
 *
 * @author xcy
 * @date 2022/8/26 - 9:59
 */
public class JwtInterceptor implements HandlerInterceptor {

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String token = request.getHeader("Authorization");
		String resultString = "";
		boolean result = true;

		TokenResult tokenResult = JwtUtils.checkToken(token);

		//判断解析的token是否为空
		if (tokenResult == null) {
			resultString = "token invalid";
			result = false;
		} else {
			//b.解析出传入的token，包含手机号以及身份标识
			String phone = tokenResult.getPhone();
			String identity = tokenResult.getIdentity();
			//通过手机号以及身份标识生成tokenKey
			String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity, TokenConstant.ACCESS_TOKEN_TYPE);
			//c.获取Redis的token
			String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
			//如果tokenRedis为空
			if (StringUtils.isBlank(tokenRedis) || (!token.trim().equals(tokenRedis.trim()))) {
				resultString = "token invalid";
				result = false;
			}
		}


		if (!result) {
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
		}
		return result;
	}
}
