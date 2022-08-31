package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/8/31 - 9:10
 */
@Service
public class DirectionService {
	/**
	 * 根据出发地和目的地的经纬度获取距离（单位：米）和时长（单位：分钟）
	 * @param depLongitude  出发地的经度
	 * @param depLatitude 出发地的纬度
	 * @param destLongitude 目的地的经度
	 * @param destLatitude 目的地的纬度
	 * @return
	 */
	public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {

		DirectionResponse directionResponse = new DirectionResponse();
		directionResponse.setDistance(123);
		directionResponse.setDuration(12);
		return ResponseResult.success(directionResponse);
	}
}
