package com.mashibing.servicedriveruser.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DriverUserStateConstant;
import com.mashibing.internalcommon.dto.DriverUser;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DriverUserExistsResponse;
import com.mashibing.servicedriveruser.mapper.DriverUserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/9/5 - 9:40
 */
@Service
@Slf4j
public class DriverUserService {

	@Autowired
	private DriverUserMapper driverUserMapper;

	/**
	 * 插入司机信息
	 *
	 * @param driverUser 司机用户
	 * @return
	 */
	public ResponseResult insertDriverUser(DriverUser driverUser) {
		LocalDateTime now = LocalDateTime.now();
		//设置插入司机信息的创建时间
		driverUser.setGmtCreate(now);
		//设置插入司机信息的更新时间
		driverUser.setGmtModified(now);

		driverUserMapper.insert(driverUser);
		return ResponseResult.success("");
	}

	/**
	 * 修改司机信息
	 *
	 * @param driverUser 司机用户
	 * @return
	 */
	public ResponseResult updateDriverUser(DriverUser driverUser) {
		LocalDateTime now = LocalDateTime.now();
		//设置修改司机信息的更新时间
		driverUser.setGmtModified(now);

		driverUserMapper.updateById(driverUser);
		return ResponseResult.success("");
	}

	/**
	 * 查询司机信息
	 *
	 * @param driverPhone 传入的司机手机号参数
	 * @return
	 */
	public ResponseResult selectDriverUser(String driverPhone) {
		Map<String, Object> map = new HashMap<>();
		//通过司机的手机号以及司机信息的状态查询司机信息
		map.put("driver_phone", driverPhone);
		map.put("state", DriverUserStateConstant.EFFECTIVE);
		List<DriverUser> driverUsers = driverUserMapper.selectByMap(map);

		DriverUserExistsResponse response = null;
		//一个手机号对应一个司机信息
		//如果driverUsers为空，或者查询到的司机手机号不一致，表示司机信息不存在
		if (driverUsers.isEmpty() || !driverUsers.get(0).getDriverPhone().equals(driverPhone)) {
			return ResponseResult.fail(CommonStatusEnum.DRIVER_NOT_EXISTS.getCode(),
					CommonStatusEnum.DRIVER_NOT_EXISTS.getMessage());
		}else {
			DriverUser driverUserDB = driverUsers.get(0);
			int isExists = DriverUserStateConstant.DRIVER_EXISTS;
			//如果司机信息的状态 == 1，表示司机信息无效
			if (driverUserDB.getState() == isExists) {
				return ResponseResult.fail(CommonStatusEnum.DRIVER_STATE_INVALID.getCode(),
						CommonStatusEnum.DRIVER_STATE_INVALID.getMessage());
			}

			response = new DriverUserExistsResponse();
			response.setDriverPhone(driverUserDB.getDriverPhone());
			response.setIsExists(isExists);
			return ResponseResult.success(response);
		}
	}
}
