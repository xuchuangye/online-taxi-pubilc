package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/8/25 - 16:52
 */
public class JwtUtils {

	private static final String SIGH = "2agharrawe&#$";

	public static String generatorToken(Map<String, String> map) {
		//设置token过期时间
		Calendar calendar = Calendar.getInstance();
		//增加1天时间
		calendar.add(Calendar.DATE, 1);

		Date date = calendar.getTime();
		JWTCreator.Builder builder = JWT.create();

		//整合Map
		map.forEach(
				builder::withClaim
		);

		//整合过期时间
		builder.withExpiresAt(date);
		//生成token
		String sign = builder.sign(Algorithm.HMAC256(SIGH));

		//返回token
		return sign;
	}

	public static void main(String[] args) {
		Map<String, String> map = new HashMap<>();
		map.put("name", "zhangsan");
		map.put("age", "18");
		String s = generatorToken(map);
		System.out.println("生成的token: " + s);
	}
}
