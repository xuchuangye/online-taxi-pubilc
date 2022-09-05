package com.mashibing.apiboss.service;

import com.mashibing.apiboss.remote.ServiceDriverUserClient;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/9/5 - 16:13
 */
@Service
@Slf4j
public class DriverUserService {

	@Autowired
	private ServiceDriverUserClient serviceDriverUserClient;

	public ResponseResult addDriverUser(DriverUser driverUser) {
		return serviceDriverUserClient.addDriverUser(driverUser);
	}

	public ResponseResult updateDriverUser(DriverUser driverUser) {
		return serviceDriverUserClient.updateDriverUser(driverUser);
	}
}
