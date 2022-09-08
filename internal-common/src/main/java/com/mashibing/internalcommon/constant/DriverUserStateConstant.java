package com.mashibing.internalcommon.constant;

import lombok.Data;

/**
 * 司机用户信息状态类
 *
 * @author xcy
 * @date 2022/9/8 - 11:37
 */
@Data
public class DriverUserStateConstant {

	/**
	 * 司机状态有效
	 */
	public static final int EFFECTIVE = 0;

	/**
	 * 司机状态无效
	 */
	public static final int INVALID = 1;

	/**
	 * 司机信息存在
	 */
	public static final int DRIVER_EXISTS = 1;
	/**
	 * 司机信息不存在
	 */
	public static final int DRIVER_NOT_EXISTS = 0;
}
