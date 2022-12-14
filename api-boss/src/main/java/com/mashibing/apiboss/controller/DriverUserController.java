package com.mashibing.apiboss.controller;

import com.mashibing.apiboss.service.CarService;
import com.mashibing.apiboss.service.DriverUserService;
import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/9/5 - 16:12
 */
@RestController
public class DriverUserController {

	@Autowired
	private DriverUserService driverUserService;


	@PostMapping("/driver-user")
	public ResponseResult addUser(@RequestBody DriverUser driverUser) {
		return driverUserService.addDriverUser(driverUser);
	}

	@PutMapping("/driver-user")
	public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
		return driverUserService.updateDriverUser(driverUser);
	}
}
