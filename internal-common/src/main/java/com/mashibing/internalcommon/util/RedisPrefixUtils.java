package com.mashibing.internalcommon.util;

/**
 * @author xcy
 * @date 2022/8/26 - 15:37
 */
public class RedisPrefixUtils {

	/**
	 * 乘客验证码的前缀
	 */
	private static final String verificationCodePrefix = "passenger-verification-code-";
	/**
	 * token令牌的前缀
	 */
	private static final String tokenPrefix = "token-";

	/**
	 * 根据乘客手机号，生成key
	 *
	 * @param passengerPhone 乘客手机号
	 * @return 返回key
	 */
	public static String generateKeyByCode(String passengerPhone) {
		//
		return verificationCodePrefix + passengerPhone;
	}

	/**
	 * 生成token令牌的key
	 *
	 * @param phone    手机号
	 * @param identity 身份标识
	 * @return
	 */
	public static String generatorTokenKey(String phone, String identity) {
		return tokenPrefix + "-" + phone + "-" + identity;
	}
}
