package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.servicemap.romate.MapDirectionClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/8/31 - 9:10
 */
@Service
public class DirectionService {

	@Autowired
	private MapDirectionClient mapDirectionClient;
	/**
	 * 根据出发地和目的地的经纬度获取距离（单位：米）和时长（单位：分钟）
	 * @param depLongitude  出发地的经度
	 * @param depLatitude 出发地的纬度
	 * @param destLongitude 目的地的经度
	 * @param destLatitude 目的地的纬度
	 * @return
	 */
	public ResponseResult driving(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
		//调用第三方接口
		DirectionResponse direction = mapDirectionClient.direction(depLongitude, depLatitude, destLongitude, destLatitude);
		return ResponseResult.success(direction);
	}
}
