package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.apidriver.remote.ServiceVerificationCodeClient;
import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverUserStateConstant;
import com.mashibing.internalcommon.constant.IdentityConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.internalcommon.response.NumberCodeResponse;
import com.mashibing.internalcommon.util.RedisPrefixUtils;
import lombok.extern.slf4j.Slf4j;
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
}
