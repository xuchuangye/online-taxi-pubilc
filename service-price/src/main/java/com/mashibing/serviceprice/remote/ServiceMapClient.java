package com.mashibing.serviceprice.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/8/31 - 16:06
 */
@FeignClient("service-map")
public interface ServiceMapClient {

	@RequestMapping(method = RequestMethod.GET, value = "/direction/driving")
	public ResponseResult<DirectionResponse> driving(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
