package com.mashibing.internalcommon.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenConstant;
import com.mashibing.internalcommon.dto.TokenResult;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 * @author xcy
 * @date 2022/8/25 - 16:52
 */
public class JwtUtils {

	private static final String SIGN = "ggagahagraiha&$@&*@";

	private static final String JWT_KEY_PHONE = "phone";
	private static final String JWT_KEY_IDENTITY = "identity";
	private static final String JWT_TOKEN_TYPE = "tokenType";
	private static final String JWT_TOKEN_TIME = "tokenTime";

	/**
	 * 生成token
	 *
	 * @param passengerPhone 乘客手机号
	 * @return
	 */
	public static String generatorToken(String passengerPhone, String identity, String tokenType) {
		Map<String, String> map = new HashMap<>();
		map.put(JWT_KEY_PHONE, passengerPhone);
		map.put(JWT_KEY_IDENTITY, identity);
		map.put(JWT_TOKEN_TYPE, tokenType);
		//为了避免相同的phone以及相同的identity生成的token总是一样的，需要加上时间戳
		map.put(JWT_TOKEN_TIME, Calendar.getInstance().getTime().toString());

		JWTCreator.Builder builder = JWT.create();
		//整合Map
		map.forEach(
				builder::withClaim
		);

		//整合过期时间，现在token已经存储到Redis中了，过期时间交给Redis控制
		//builder.withExpiresAt(date);

		//生成token
		//给定算法签名创建新的JWT
		String sign = builder.sign(Algorithm.HMAC256(SIGN));

		//返回token
		return sign;
	}

	/**
	 * 解析token
	 *
	 * @param token 令牌
	 * @return
	 */
	public static TokenResult parseToken(String token) {
		//返回一个JWTVerifier构建器，其中包含用于验证令牌签名的算法
		//使用已提供的配置创建JWTVerifier的新且可重用实例
		//使用任何先前配置的选项对给定的令牌执行验证
		DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
		//一定是asString()，而不是toString()，否则会出现""  ""
		String phone = verify.getClaim(JWT_KEY_PHONE).asString();
		String identity = verify.getClaim(JWT_KEY_IDENTITY).asString();

		TokenResult tokenResult = new TokenResult();
		tokenResult.setPhone(phone);
		tokenResult.setIdentity(identity);
		return tokenResult;
	}

	/**
	 * 校验token，判断token是否异常
	 * @param token
	 * @return
	 */
	public static TokenResult checkToken(String token) {
		TokenResult tokenResult = null;
		try {
			//a.对传入的请求头信息进行解析，解析出token
			tokenResult = JwtUtils.parseToken(token);
		}
		//其余异常
		catch (Exception e) {

		}
		return tokenResult;
	}

	public static void main(String[] args) {
		String accessToken = generatorToken("13875823291", IdentityConstant.PASSENGER, TokenConstant.ACCESS_TOKEN_TYPE);
		System.out.println("生成的token: " + accessToken);

		System.out.println("解析的token");
		TokenResult tokenResult = parseToken(accessToken);
		System.out.println("手机号：" + tokenResult.getPhone());
		System.out.println("身份标识：" + tokenResult.getIdentity());
	}

}
