package com.mashibing.apidriver.service;

import com.mashibing.apidriver.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/9/6 - 9:20
 */
@Service
public class DriverService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	public ResponseResult updateDriverUser(DriverUser driverUser) {
		return serviceDriverUserClient.updateUser(driverUser);
	}
}
