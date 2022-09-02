package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.romate.MapDicDistrictClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/9/2 - 8:26
 */
@Service
public class DicDistrictService {

	@Autowired
	private MapDicDistrictClient mapDicDistrictClient;

	public ResponseResult initDicDistrict(String keywords, String level) {
		//组装地图字典表的URL
		String dicDistrict = mapDicDistrictClient.dicDistrict(keywords, level);
		System.out.println(dicDistrict);
		//解析URL

		//插入数据库

		return ResponseResult.success();
	}
}
