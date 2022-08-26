package com.mashibing.apipassenger.service;

import com.mashibing.apipassenger.remote.ServicePassengerUserClient;
import com.mashibing.apipassenger.remote.ServiceVerificationcodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 通过该service调用验证码服务service-verificationcode
 *
 * @author xcy
 * @date 2022/8/20 - 16:24
 */
@Service
public class VerificationCodeService {
	@Autowired
	private ServiceVerificationcodeClient serviceVerificationcodeClient;
	/**
	 * 验证码前缀
	 */
	private final String verificationCodePrefix = "passenger-verification-code-";
	/**
	 * token令牌的前缀
	 */
	private final String tokenPrefix = "token-";
	/**
	 * key与value都是String类型额，所以使用StringRedisTemplate
	 */
	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 通过乘客手机号调用验证码服务，获取验证码信息响应给乘客
	 *
	 * @param passengerPhone 乘客手机号
	 * @return 响应状态码、message，以及验证码信息
	 */
	public ResponseResult generatorCode(String passengerPhone) {
		//调用验证码服务
		//FeignClient可变参数的验证码位数
		ResponseResult<NumberCodeResponse> numberCodeResponse = serviceVerificationcodeClient.getNumberCode(6);
		int numberCode = numberCodeResponse.getData().getNumberCode();
		//存储到Redis
		String key = generateKeyByCode(passengerPhone);
		//将验证码存储到Redis，过期时间2分钟
		stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);
		//通过短信服务商，将对应的验证码发送到手机上
		//比如：阿里的短信服务，腾讯的短信通，华信，容联
		return ResponseResult.success();
	}

	/**
	 * 根据手机号，生成key
	 *
	 * @param passengerPhone
	 * @return 返回key
	 */
	private String generateKeyByCode(String passengerPhone) {
		//
		return verificationCodePrefix + passengerPhone;
	}

	/**
	 * 生成token令牌的key
	 *
	 * @param phone    手机号
	 * @param identity 身份标识
	 * @return
	 */
	private String generatorTokenKey(String phone, String identity) {
		return tokenPrefix + "-" + phone + "-" + identity;
	}

	@Autowired
	private ServicePassengerUserClient servicePassengerUserClient;

	/**
	 * 校验验证码
	 *
	 * @param passengerPhone   乘客手机号
	 * @param verificationCode 验证码
	 * @return
	 */
	public ResponseResult checkVerificationCode(String passengerPhone, String verificationCode) {
		//根据乘客手机号，从Redis中获取验证码
		String key = generateKeyByCode(passengerPhone);
		String codeRedis = stringRedisTemplate.opsForValue().get(key);
		//校验验证码
		System.out.println("从Redis中获取的验证码: " + codeRedis);
		//判断Redis是否存在对应的乘客手机号，进行处理

		//检查从Redis中获取的验证码是否为空格、空 ("") 或 null
		if (StringUtils.isBlank(codeRedis)) {
			return ResponseResult.fail(CommonStatusEnum.VERIFICATIONCODE_ERROR.getCode(),
					CommonStatusEnum.VERIFICATIONCODE_ERROR.getMessage());
		}
		//乘客输入的验证码和Redis存储的验证码不一致，表示验证码不正确
		assert codeRedis != null;
		if (!verificationCode.trim().equals(codeRedis.trim())) {
			return ResponseResult.fail(CommonStatusEnum.VERIFICATIONCODE_ERROR.getCode(),
					CommonStatusEnum.VERIFICATIONCODE_ERROR.getMessage());
		}

		//远程调用service-passenger-user
		VerificationCodeDTO verificationCodeDTO = new VerificationCodeDTO();
		verificationCodeDTO.setPassengerPhone(passengerPhone);
		servicePassengerUserClient.loginOrRegister(verificationCodeDTO);

		//颁发令牌
		String token = JwtUtils.generatorToken(passengerPhone, IdentityConstant.PASSENGER);

		//将令牌存放在Redis中
		String tokenKey = generatorTokenKey(passengerPhone, IdentityConstant.PASSENGER);
		stringRedisTemplate.opsForValue().set(tokenKey, token, 30, TimeUnit.DAYS);

		//响应token
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setToken(token);
		return ResponseResult.success(tokenResponse);
	}
}
