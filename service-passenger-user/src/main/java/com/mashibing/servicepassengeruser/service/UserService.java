package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.dto.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/8/25 - 9:24
 */
@Service
public class UserService {

	@Autowired
	private PassengerUserMapper passengerUserMapper;

	public ResponseResult loginOrRegister(String passengerPhone) {
		System.out.println("user service被调用，手机号：" + passengerPhone);
		//根据手机号查询用户信息
		Map<String, Object> map = new HashMap<>();
		map.put("passenger_phone", passengerPhone);
		List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
		//判断乘客手机号是否存在
		if (passengerUsers == null || passengerUsers.size() == 0) {
			//如果不存在，则需要插入用户信息
			PassengerUser passengerUser = new PassengerUser();
			passengerUser.setPassengerPhone(passengerPhone);
			passengerUser.setPassengerName("张三");
			passengerUser.setPassengerGender((byte) 0);
			passengerUser.setState((byte) 0);
			LocalDateTime now = LocalDateTime.now();
			passengerUser.setGmtCreate(now);
			passengerUser.setGmtModified(now);
			passengerUserMapper.insert(passengerUser);
		}
		return ResponseResult.success();
	}

	public ResponseResult getUserByPhone(String passengerPhone) {
		Map<String,  Object> map = new HashMap<>();
		map.put("passenger_phone", passengerPhone);
		List<PassengerUser> passengerUsers = passengerUserMapper.selectByMap(map);
		if (passengerUsers == null || passengerUsers.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.USER_NOT_EXISTS.getCode(), CommonStatusEnum.USER_NOT_EXISTS.getMessage());
		}else {
			PassengerUser passengerUser = passengerUsers.get(0);
			passengerUser.setProfilePhoto("头像");
			return ResponseResult.success(passengerUser);
		}
	}
}
