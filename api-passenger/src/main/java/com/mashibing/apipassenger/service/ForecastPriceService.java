package com.mashibing.apipassenger.service;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/8/30 - 15:47
 */
@Service
@Slf4j
public class ForecastPriceService {

	/**
	 * @param depLongitude  出发地经度
	 * @param depLatitude   出发地纬度
	 * @param destLongitude 目的地经度
	 * @param destLatitude  目的地纬度
	 * @return
	 */
	public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
		log.info("出发地经度:" + depLongitude);
		log.info("出发地纬度:" + depLatitude);
		log.info("目的地经度:" + destLongitude);
		log.info("目的地纬度:" + destLatitude);

		log.info("调用计价服务service-price，计算价格");

		ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
		forecastPriceResponse.setPrice(12.34);
		return ResponseResult.success(forecastPriceResponse);
	}
}
