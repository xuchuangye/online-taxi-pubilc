package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * @author xcy
 * @date 2022/8/31 - 17:27
 */
@Data
public class PriceRule {

	/**
	 * 城市代码
	 */
	private String cityCode;
	/**
	 * 车辆类型
	 */
	private String vehicleType;
	/**
	 * 起步价
	 */
	private Double startFare;
	/**
	 * 起步里程
	 */
	private Integer startMile;
	/**
	 * 计程单价
	 */
	private Double unitPricePreMile;
	/**
	 * 计时单价
	 */
	private Double unitPricePreMinute;
}
