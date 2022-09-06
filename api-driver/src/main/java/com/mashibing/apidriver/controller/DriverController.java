package com.mashibing.apidriver.controller;

import com.mashibing.apidriver.service.DriverService;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/9/6 - 9:18
 */
@RestController
public class DriverController {

	@Autowired
	private DriverService driverService;

	@PutMapping("/driver-user")
	public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
		return driverService.updateDriverUser(driverUser);
	}
}
