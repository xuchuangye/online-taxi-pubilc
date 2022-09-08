package com.mashibing.servicedriveruser.controller;

import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author xcy
 * @date 2022/9/5 - 14:51
 */
@RestController
public class DriverUserController {
	@Autowired
	private DriverUserService driverUserService;

	/**
	 * 插入司机信息
	 * @param driverUser
	 * @return
	 */
	@PostMapping("/user")
	public ResponseResult insertUser(@RequestBody DriverUser driverUser) {
		return driverUserService.insertDriverUser(driverUser);
	}

	/**
	 * 修改司机信息
	 * @param driverUser
	 * @return
	 */
	@PutMapping("/user")
	public ResponseResult updateUser(@RequestBody DriverUser driverUser) {
		return driverUserService.updateDriverUser(driverUser);
	}

	/**
	 * 查询司机信息
	 * @param driverPhone 司机手机号
	 * @return
	 */
	@GetMapping("/check-driver/{driverPhone}")
	public ResponseResult selectUser(@PathVariable("driverPhone") String driverPhone) {
		return driverUserService.selectDriverUser(driverPhone);
	}
}
