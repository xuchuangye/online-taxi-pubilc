package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2022/8/24 - 17:09
 */
@Data
public class TokenResponse {
	/**
	 * 访问token令牌
	 */
	private String accessToken;
	/**
	 * 刷新token令牌
	 */
	private String refreshToken;
}
