package com.mashibing.internalcommon.dto;

import lombok.Data;

/**
 * 地区字典表对应的实体类
 * @author xcy
 * @date 2022/9/1 - 17:37
 */
@Data
public class DicDistrict {
	/**
	 * 地区编码
	 */
	private String addressCode;
	/**
	 * 地区名称
	 */
	private String addressName;
	/**
	 * 上一级地区编码
	 */
	private String parentAddressCode;
	/**
	 * 级别
	 */
	private int level;
}
