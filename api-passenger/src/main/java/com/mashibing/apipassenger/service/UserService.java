package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.TokenResult;
import com.mashibing.internalcommon.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 业务逻辑调用在Service
 * @author xcy
 * @date 2022/8/29 - 15:18
 */
@Service
@Slf4j
public class UserService {

	public ResponseResult getUserByAccessToken(String accessToken) {
		log.info("accessToken: " + accessToken);

		//根据accessToken解析出手机号
		TokenResult tokenResult = JwtUtils.checkToken(accessToken);
		String phone = tokenResult.getPhone();
		log.info("phone: " + phone);

		//根据手机号查询乘客用户信息


		PassengerUser passengerUser = new PassengerUser();
		passengerUser.setPassengerName("张三");
		passengerUser.setProfilePhoto("头像");
		return ResponseResult.success(passengerUser);
	}
}
