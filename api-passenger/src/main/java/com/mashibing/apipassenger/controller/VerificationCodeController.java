package com.mashibing.apipassenger.controller;

import com.mashibing.apipassenger.service.VerificationCodeService;
import com.mashibing.internalcommon.request.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/8/20 - 16:15
 */
@RestController
public class VerificationCodeController {

	@Autowired
	private VerificationCodeService verificationCodeService;

	@GetMapping("/verification-code")
	public ResponseResult verificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		String passengerPhone = verificationCodeDTO.getPassengerPhone();
		System.out.println("获取乘客手机号：" + passengerPhone);

		return verificationCodeService.generatorCode(passengerPhone);
	}

	@PostMapping("/verification-code-check")
	public ResponseResult verificationCodeCheck(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		//获取乘客手机号
		String passengerPhone = verificationCodeDTO.getPassengerPhone();
		//获取验证码
		String verificationCode = verificationCodeDTO.getVerificationCode();
		System.out.println("乘客手机号: " + passengerPhone + "，验证码: " + verificationCode);

		return verificationCodeService.checkVerificationCode(passengerPhone, verificationCode);
	}
}
