package com.mashibing.internalcommon.request;


import lombok.Data;

/**
 * 封装请求参数的JavaBean
 *
 * @author xcy
 * @date 2022/8/20 - 16:18
 */
@Data
public class VerificationCodeDTO {
	/**
	 * 乘客手机号
	 */
	private String passengerPhone;

	/**
	 * 验证码
	 */
	private String verificationCode;

}
