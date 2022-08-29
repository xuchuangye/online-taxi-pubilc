package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.TokenService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author xcy
 * @date 2022/8/29 - 9:18
 */
@RestController
public class TokenController {

	@Autowired
	private TokenService tokenService;

	@PostMapping("/token-refresh")
	public ResponseResult refreshToken(@RequestBody TokenResponse tokenResponse) {
		String refreshToken = tokenResponse.getRefreshToken();
		System.out.println("refreshToken: " + refreshToken);

		return tokenService.refreshToken(refreshToken);
	}
}
