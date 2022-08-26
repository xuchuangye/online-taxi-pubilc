package com.mashibing.apipassenger.interceptor;

import com.alibaba.nacos.common.utils.StringUtils;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
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

		TokenResult tokenResult = null;
		try {
			//a.对传入的请求头信息进行解析，解析出token
			tokenResult = JwtUtils.parseToken(token);
		}
		//签名验证异常
		catch (SignatureVerificationException e) {
			resultString = "token sign error";
			result = false;
		}
		//token令牌过期异常
		catch (TokenExpiredException e) {
			resultString = "token time out";
			result = false;
		}
		//算法不匹配异常
		catch (AlgorithmMismatchException e) {
			resultString = "token AlgorithmMismatchException";
			result = false;
		}
		//其余异常
		catch (Exception e) {
			resultString = "token invalid";
			result = false;
		}

		//判断解析的token是否为空
		if (tokenResult == null) {
			resultString = "token invalid";
			result = false;
		} else {
			//b.解析出传入的token，包含手机号以及身份标识
			String phone = tokenResult.getPhone();
			String identity = tokenResult.getIdentity();
			//通过手机号以及身份标识生成tokenKey
			String tokenKey = RedisPrefixUtils.generatorTokenKey(phone, identity);
			//c.获取Redis的token
			String tokenRedis = stringRedisTemplate.opsForValue().get(tokenKey);
			//如果tokenRedis为空
			if (StringUtils.isBlank(tokenRedis)) {
				resultString = "token invalid";
				result = false;
			}else {
				//d.判断Redis获取的token和传入的token是否一样
				if (!token.trim().equals(tokenRedis.trim())) {
					resultString = "token invalid";
					result = false;
				}
			}
		}


		if (!result) {
			PrintWriter writer = response.getWriter();
			writer.print(JSONObject.fromObject(ResponseResult.fail(resultString)).toString());
		}
		return result;
	}
}
