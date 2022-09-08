package com.mashibing.internalcommon.util;

/**
 * @author xcy
 * @date 2022/8/26 - 15:37
 */
public class RedisPrefixUtils {

	/**
	 * 乘客验证码的前缀
	 */
	private static final String verificationCodePrefix = "verification-code-";

	/**
	 * token令牌的前缀
	 */
	private static final String tokenPrefix = "token-";

	/**
	 * 根据身份标识以及手机号，生成key
	 *
	 * @param phone    手机号
	 * @param identity 身份标识
	 * @return 返回key
	 */
	public static String generateKeyByCode(String phone, String identity) {
		//identity-verification-code-phone
		return verificationCodePrefix + identity + "-" + phone;
	}

	/**
	 * 生成token令牌的key
	 *
	 * @param phone     手机号
	 * @param identity  身份标识
	 * @param tokenType 令牌的类型
	 * @return
	 */
	public static String generatorTokenKey(String phone, String identity, String tokenType) {
		return tokenPrefix + "-" + phone + "-" + identity + "-" + tokenType;
	}
}
