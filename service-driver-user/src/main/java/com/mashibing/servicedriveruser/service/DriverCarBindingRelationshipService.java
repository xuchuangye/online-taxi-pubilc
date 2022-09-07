package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.DriverCarBindingConstant;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author xcy
 * @since 2022-09-07
 */
@Service
public class DriverCarBindingRelationshipService {

	@Autowired
	private DriverCarBindingRelationshipMapper driverCarBindingRelationshipMapper;

	/**
	 * 司机与车辆进行绑定
	 * @param driverCarBindingRelationship
	 * @return
	 */
	public ResponseResult bind(DriverCarBindingRelationship driverCarBindingRelationship) {
		LocalDateTime now = LocalDateTime.now();
		//设置绑定时间
		driverCarBindingRelationship.setBindingTime(now);
		//设置绑定状态
		driverCarBindingRelationship.setBindState(DriverCarBindingConstant.DRIVER_CAR_BIND);

		driverCarBindingRelationshipMapper.insert(driverCarBindingRelationship);
		return ResponseResult.success("");
	}
}
