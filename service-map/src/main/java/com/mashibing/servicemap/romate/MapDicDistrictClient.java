package com.mashibing.servicemap.romate;

import com.mashibing.internalcommon.constant.DirectionConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * 地图地区
 * 组装地图地区字典表的URL
 * @author xcy
 * @date 2022/9/2 - 8:55
 */
@Service
public class MapDicDistrictClient {

	@Value("${amap.key}")
	private String amapKey;

	@Autowired
	private RestTemplate restTemplate;

	public String dicDistrict(String keywords) {
		//组装地图字典表的URL
		//https://restapi.amap.com/v3/config/district?keywords=中国&subdistrict=3&key=9e0a1d62cd4b196f8cd73a81a3899bc1
		StringBuilder url = new StringBuilder();
		//https://restapi.amap.com/v3/config/district
		url.append(DirectionConstant.DICDISTRICT_URL)
				.append("?")
				.append("keywords=").append(keywords)
				.append("&")
				.append("subdistrict=3")
				.append("&")
				.append("key=").append(amapKey);
		System.out.println(url.toString());
		ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
		return forEntity.getBody();
	}
}
