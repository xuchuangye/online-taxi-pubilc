package com.mashibing.internalcommon.constant;


import lombok.Getter;

/**
 * 根据不同的状态码创建枚举类
 * @author xcy
 * @date 2022/8/22 - 18:25
 */
public enum CommonStatusEnum {

	/**
	 * 验证码范围：1000 ~ 1099
	 */
	VERIFICATIONCODE_ERROR(1099, "验证码不正确"),

	/**
	 * 成功
	 */
	SUCCESS(1, "success"),
	/**
	 * 失败
	 */
	FAIL(0, "fail");

	/**
	 * 状态码
	 */
	@Getter
	private final int code;
	/**
	 * 提示信息
	 */
	@Getter
	private final String message;

	CommonStatusEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
}
