package com.mashibing.servicedriveruser.controller;


import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
public class CarController {

	@Autowired
	private CarService carService;

	@PostMapping("/car")
	public ResponseResult addCar(@RequestBody Car car) {
		return carService.addCar(car);
	}
}
