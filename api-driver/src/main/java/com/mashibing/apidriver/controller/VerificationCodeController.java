package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2022/9/8 - 8:40
 */
@RestController
public class VerificationCodeController {

	@Autowired
	private VerificationCodeService verificationCodeService;

	/**
	 * 获取验证码
	 * @param verificationCodeDTO
	 * @return
	 */
	@GetMapping("/verification-code")
	public ResponseResult getVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		String driverPhone = verificationCodeDTO.getDriverPhone();
		return verificationCodeService.getVerificationcode(driverPhone);
	}

	/**
	 * 校验验证码
	 * @param verificationCodeDTO
	 * @return
	 */
	@PostMapping("/verification-code-check")
	public ResponseResult checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		String driverPhone = verificationCodeDTO.getDriverPhone();
		String verificationCode = verificationCodeDTO.getVerificationCode();
		return verificationCodeService.checkcheckVerificationCode(driverPhone, verificationCode);
	}
}
