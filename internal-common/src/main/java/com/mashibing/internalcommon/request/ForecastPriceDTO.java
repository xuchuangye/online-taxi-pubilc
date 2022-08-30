package com.mashibing.internalcommon.request;

import lombok.Data;

/**
 * 预估价格的请求参数（经度纬度）实体类
 * @author xcy
 * @date 2022/8/30 - 15:44
 */
@Data
public class ForecastPriceDTO {
	/**
	 * 出发地经度
	 */
	private String depLongitude;
	/**
	 * 出发地纬度
	 */
	private String depLatitude;
	/**
	 * 目的地经度
	 */
	private String destLongitude;
	/**
	 * 目的地纬度
	 */
	private String destLatitude;
}
