package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xcy
 * @date 2022/9/5 - 9:39
 */
@RestController
public class TestController {

	@Autowired
	private DriverUserService driverUserService;
	@GetMapping("/test")
	public String test() {
		return "test";
	}

	/*@GetMapping("/test-ds")
	public ResponseResult testDataSource() {
		return driverUserService.testGetDriverUser();
	}*/
}
