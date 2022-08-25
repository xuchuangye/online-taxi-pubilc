package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/8/25 - 16:52
 */
public class JwtUtils {

	public static final String SIGN = "ggagahagraiha&$@&*@";
	public static final String JWT_KEY = "passengerPhone";

	/**
	 * 生成token
	 * @param passengerPhone 乘客手机号
	 * @return
	 */
	public static String generatorToken(String passengerPhone) {
		Map<String, String> map = new HashMap<>();
		map.put(JWT_KEY, passengerPhone);

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
		String sign = builder.sign(Algorithm.HMAC256(SIGN));

		//返回token
		return sign;
	}

	/**
	 * 解析token
	 * @param token 令牌
	 * @return
	 */
	public static String parseToken(String token) {
		DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
		Claim claim = verify.getClaim(JWT_KEY);
		return claim.toString();
	}

	public static void main(String[] args) {
		String token = generatorToken("13875823291");
		System.out.println("生成的token: " + token);

		System.out.println("解析的token：" + parseToken(token));
	}

}
