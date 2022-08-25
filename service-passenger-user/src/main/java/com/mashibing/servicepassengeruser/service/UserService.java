package com.mashibing.servicepassengeruser.service;

import com.mashibing.internalcommon.request.ResponseResult;
import com.mashibing.servicepassengeruser.DTO.PassengerUser;
import com.mashibing.servicepassengeruser.mapper.PassengerUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		if (passengerUsers == null || passengerUsers.size() == 0) {
			System.out.println("无记录");
		}else {
			System.out.println(passengerUsers.get(0).getPassengerPhone());
			System.out.println(passengerUsers.get(0).getPassengerName());
			System.out.println(passengerUsers.get(0).getPassengerGender());
		}
		//判断乘客手机号是否存在

		//如果不存在，则需要插入用户信息

		return ResponseResult.success();
	}
}
