package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.DriverCarBindingRelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author xcy
 * @since 2022-09-07
 */
@RestController
@RequestMapping("/driver-car-binding-relationship")
public class DriverCarBindingRelationshipController {

	@Autowired
	private DriverCarBindingRelationshipService driverCarBindingRelationshipService;

	@PostMapping("/bind")
	public ResponseResult bind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {

		return driverCarBindingRelationshipService.bind(driverCarBindingRelationship);
	}

	@PostMapping("/unbind")
	public ResponseResult unbind(@RequestBody DriverCarBindingRelationship driverCarBindingRelationship) {
		return  driverCarBindingRelationshipService.unbind(driverCarBindingRelationship);
	}
}
