package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.VerificationCodeService;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
	public ResponseResult getVerificationcode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
		String driverPhone = verificationCodeDTO.getDriverPhone();
		return verificationCodeService.getVerificationcode(driverPhone);
	}
}
