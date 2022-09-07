package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverCarBindingConstant;
import com.mashibing.internalcommon.dto.DriverCarBindingRelationship;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicedriveruser.mapper.DriverCarBindingRelationshipMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
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
	 *
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

	/**
	 * 司机与车辆进行解绑
	 *
	 * @param driverCarBindingRelationship
	 * @return
	 */
	public ResponseResult unbind(DriverCarBindingRelationship driverCarBindingRelationship) {
		Map<String, Object> map = new HashMap<>();
		map.put("driver_id", driverCarBindingRelationship.getDriverId());
		map.put("car_id", driverCarBindingRelationship.getCarId());
		map.put("bind_state", DriverCarBindingConstant.DRIVER_CAR_BIND);

		List<DriverCarBindingRelationship> driverCarBindingRelationships = driverCarBindingRelationshipMapper.selectByMap(map);

		if (driverCarBindingRelationships.isEmpty()) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_CAR_BIND_NOT_EXISTS.getMessage());
		}else {
			//解绑之后的司机与车辆的绑定关系
			DriverCarBindingRelationship postDriverCarBindingRelationship = driverCarBindingRelationships.get(0);
			LocalDateTime now = LocalDateTime.now();
			//设置解绑时间
			postDriverCarBindingRelationship.setUnBindingTime(now);
			//设置解绑状态
			postDriverCarBindingRelationship.setBindState(DriverCarBindingConstant.DRIVER_CAR_UNBIND);

			driverCarBindingRelationshipMapper.updateById(postDriverCarBindingRelationship);
			return ResponseResult.success("");
		}
	}
}
