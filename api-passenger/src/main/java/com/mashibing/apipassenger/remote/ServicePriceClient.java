package com.mashibing.apipassenger.remote;

import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author xcy
 * @date 2022/9/1 - 9:24
 */
@FeignClient("service-price")
public interface ServicePriceClient {
	/**
	 * 请求参数以@RequestBody的方式并且以POST请求方式时，不需要添加feign-httpclient的依赖
	 * @param forecastPriceDTO 预估价格的请求参数（经度纬度）实体类对象
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/forecast-price")
	public ResponseResult<ForecastPriceResponse> forecast(@RequestBody ForecastPriceDTO forecastPriceDTO);
}
