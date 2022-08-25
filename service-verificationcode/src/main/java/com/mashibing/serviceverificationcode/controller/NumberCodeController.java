package com.mashibing.serviceverificationcode.controller;

import com.mashibing.internalcommon.request.ResponseResult;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/8/22 - 17:08
 */
@RestController
public class NumberCodeController {

	@GetMapping("/numberCode/{size}")
	public ResponseResult numberCode(@PathVariable("size") int size) {
		System.out.println("size: " + size);
		//随机生成验证码
		double res = (Math.random() * 9 + 1) * Math.pow(10, size - 1);
		int numberCode = (int) (res);
		System.out.println("随机生成的验证码: " + numberCode);

		//响应numberCode
		NumberCodeResponse response = new NumberCodeResponse();
		response.setNumberCode(numberCode);

		return ResponseResult.success(response);
	}
}
