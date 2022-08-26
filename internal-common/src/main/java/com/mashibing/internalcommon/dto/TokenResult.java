package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @author xcy
 * @date 2022/8/26 - 8:55
 */
@Data
public class TokenResult {
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 身份标识
	 */
	private String identity;
}
