package com.mashibing.internalcommon.response;

import lombok.Data;

/**
 * @author xcy
 * @date 2022/8/31 - 9:31
 */
@Data
public class DirectionResponse {
	/**
	 * 距离（单位：米）
	 */
	private Integer distance;
	/**
	 * 时长（单位：分钟）
	 */
	private Integer duration;
}
