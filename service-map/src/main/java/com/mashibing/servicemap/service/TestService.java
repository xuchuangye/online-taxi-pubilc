package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/8/31 - 9:10
 */
@Service
@Slf4j
public class TestService {

	@Autowired
	private DicDistrictMapper dicDistrictMapper;

	public String test() {
		Map<String, Object> map = new HashMap<>();
		map.put("address_code", "110000");

		List<DicDistrict> dicDistricts = dicDistrictMapper.selectByMap(map);
		log.info("dicDistrict: " + dicDistricts.get(0));
		return "test service map";
	}
}
