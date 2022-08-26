package com.mashibing.apipassenger.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/8/26 - 9:57
 */
@RestController
public class TestController {

	@GetMapping("/authTest")
	public ResponseResult authTest() {
		return ResponseResult.success("auth test");
	}

	@GetMapping("/noauthTest")
	public ResponseResult noauthTest() {
		return ResponseResult.success("no auth test");
	}
}
