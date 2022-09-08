package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverUserStateConstant;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.constant.TokenConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.VerificationCodeDTO;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.response.TokenResponse;
import com.mashibing.internalcommon.util.JwtUtils;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author xcy
 * @date 2022/9/8 - 8:41
 */
@Service
@Slf4j
public class VerificationCodeService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	@Autowired
	private ServiceVerificationCodeClient serviceVerificationCodeClient;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	/**
	 * 获取验证码
	 * @param driverPhone 司机手机号
	 * @return
	 */
	public ResponseResult getVerificationcode(String driverPhone) {
		//调用service-driver-user服务，查询司机信息是否存在
		ResponseResult<DriverUserExistsResponse> driverUserExistsResponseResponseResult = serviceDriverUserClient.selectUser(driverPhone);
		DriverUserExistsResponse data = driverUserExistsResponseResponseResult.getData();
		Integer isExists = data.getIsExists();
		//如果isExists == 0，表示司机信息不存在
		if (isExists == DriverUserStateConstant.DRIVER_NOT_EXISTS) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
		}
		String driverPhoneDB = data.getDriverPhone();
		//如果传入的司机手机号和
		if (!driverPhoneDB.trim().equals(driverPhoneDB.trim())) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
		}
		log.info("司机存在");

		//获取验证码
		ResponseResult<NumberCodeResponse> verificationcode = serviceVerificationCodeClient.getVerificationCode(6);
		NumberCodeResponse response = verificationcode.getData();
		int numberCode = response.getNumberCode();
		log.info("验证码：" + numberCode);
		//TODO 第三方发送验证码

		//将验证码存储到Redis
		String key = RedisPrefixUtils.generateKeyByCode(driverPhone, IdentityConstant.DRIVER);
		stringRedisTemplate.opsForValue().set(key, numberCode + "", 2, TimeUnit.MINUTES);

		return ResponseResult.success("");
	}

	/**
	 * 校验验证码
	 * @param driverPhone 司机手机号
	 * @param verificationCode 验证码
	 * @return
	 */
	public ResponseResult checkcheckVerificationCode(String driverPhone, String verificationCode) {
		//根据乘客手机号，从Redis中获取验证码
		String key = RedisPrefixUtils.generateKeyByCode(driverPhone, IdentityConstant.DRIVER);
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
		verificationCodeDTO.setPassengerPhone(driverPhone);
		serviceDriverUserClient.loginOrRegister(verificationCodeDTO);

		//颁发令牌
		String accessToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER, TokenConstant.ACCESS_TOKEN_TYPE);
		String refreshToken = JwtUtils.generatorToken(driverPhone, IdentityConstant.DRIVER, TokenConstant.REFRESH_TOKEN_TYPE);

		//将令牌存放在Redis中
		String accessTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.DRIVER, TokenConstant.ACCESS_TOKEN_TYPE);
		String refreshTokenKey = RedisPrefixUtils.generatorTokenKey(driverPhone, IdentityConstant.DRIVER, TokenConstant.REFRESH_TOKEN_TYPE);

		stringRedisTemplate.opsForValue().set(accessTokenKey, accessToken, 30, TimeUnit.DAYS);
		stringRedisTemplate.opsForValue().set(refreshTokenKey, refreshToken, 31, TimeUnit.DAYS);

		//响应token
		TokenResponse tokenResponse = new TokenResponse();
		tokenResponse.setAccessToken(accessToken);
		tokenResponse.setRefreshToken(refreshToken);
		return ResponseResult.success(tokenResponse);
	}
}
