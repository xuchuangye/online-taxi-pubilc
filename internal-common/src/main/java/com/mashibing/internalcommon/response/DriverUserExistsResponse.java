package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * 司机信息是否存在的返回响应类
 * @author xcy
 * @date 2022/9/8 - 8:49
 */
@Data
public class DriverUserExistsResponse {

	/**
	 * 司机信息
	 */
	private String driverPhone;

	/**
	 * 司机信息的是否存在
	 */
	private Integer isExists;
}
