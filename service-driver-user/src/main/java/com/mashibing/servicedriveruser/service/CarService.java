package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.dto.Car;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.CarMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * @author xcy
 * @date 2022/9/7 - 8:51
 */
@Service
public class CarService {

	@Autowired
	private CarMapper carMapper;

	/**
	 * 插入车辆信息
	 * @param car
	 * @return
	 */
	public ResponseResult addCar(Car car) {
		LocalDateTime now = LocalDateTime.now();
		//创建时间
		car.setGmtCreate(now);
		//修改时间
		car.setGmtModified(now);

		carMapper.insert(car);
		return ResponseResult.success("");
	}
}
