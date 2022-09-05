package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/9/5 - 9:40
 */
@Service
@Slf4j
public class DriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	public ResponseResult testGetDriverUser() {
		Map<String, Object> map = new HashMap<>();
		map.put("id", 1);
		List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);
		DriverUser driverUser = driverUsers.get(0);
		log.info("driverUser: " + driverUser);
		return ResponseResult.success(driverUsers);
	}
}
