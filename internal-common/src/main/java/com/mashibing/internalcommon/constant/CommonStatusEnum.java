package com.mashibing.internalcommon.constant;


import lombok.Getter;

/**
 * 根据不同的状态码创建枚举类
 *
 * @author xcy
 * @date 2022/8/22 - 18:25
 */
public enum CommonStatusEnum {

	/**
	 * 验证码范围：1000 ~ 1099
	 */
	VERIFICATIONCODE_ERROR(1099, "验证码不正确"),

	/**
	 * token错误提示：1100 ~ 1199
	 */
	TOKEN_ERROR(1199, "token错误"),

	/**
	 * 用户提示信息：1200 ~ 1299
	 */
	USER_NOT_EXISTS(1299, "当前用户不存在"),

	/**
	 * 计价规则提示信息：1300 ~ 1399
	 */
	PRICE_RULE_NOT_EXISTS(1399, "计价规则不存在"),

	/**
	 * 地图地区字典表不存在：1400 ~ 1499
	 */
    MAP_DISTRICT_NOT_EXISTS(1499, "地图地区字典表不存在"),

	/**
	 * 司机与车辆不存在绑定关系：1500 ~ 1599
	 */
	DRIVER_CAR_BIND_NOT_EXISTS(1599, "司机与车辆不存在绑定关系"),

	/**
	 * 司机不存在：1600 ~ 1699
	 */
	DRIVER_NOT_EXISTS(1699, "司机信息不存在"),

	/**
	 * 司机信息无效：1700 ~ 1799
	 */
	DRIVER_STATE_INVALID(1799, "司机信息无效"),

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
