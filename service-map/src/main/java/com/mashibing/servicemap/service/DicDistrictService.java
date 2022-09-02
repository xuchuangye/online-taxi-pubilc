package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/9/2 - 8:26
 */
@Service
public class DicDistrictService {

	@Value("${amap.key}")
	private String amapKey;

	public ResponseResult initDicDistrict(String keywords, String level) {
		//组装地图字典表的URL
		//?keywords=北京&subdistrict=2&key=<用户的key>
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(DirectionConstant.DICDISTRICT_URL)
				.append("?")
				.append("keywords=").append(keywords)
				.append("&")
				.append("subdistrict=").append(level)
				.append("&")
				.append("key=").append(amapKey);

		//解析URL

		//
		return ResponseResult.success();
	}
}
