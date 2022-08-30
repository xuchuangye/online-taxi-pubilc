package com.mashibing.servicepassengeruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.servicepassengeruser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/8/25 - 9:21
 */
@RestController
public class UserController {
	@Autowired
	private UserService userService;

	@PostMapping("/user")
	public ResponseResult loginOrRegister(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		String passengerPhone = verificationCodeDTO.getPassengerPhone();
		System.out.println("乘客手机号: " + passengerPhone);

		return userService.loginOrRegister(passengerPhone);
	}

	@GetMapping("/user/")
	public ResponseResult getUser(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		String passengerPhone = verificationCodeDTO.getPassengerPhone();
		return userService.getUserByPhone(passengerPhone);
	}
}
