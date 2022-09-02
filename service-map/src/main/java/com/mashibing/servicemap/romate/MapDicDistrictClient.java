package com.mashibing.servicemap.romate;

import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.dto.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2022/9/2 - 8:55
 */
@Service
public class MapDicDistrictClient {

	@Value("${amap.key}")
	private String amapKey;

	@Autowired
	private RestTemplate restTemplate;

	public String dicDistrict(String keywords, String level) {
		//组装地图字典表的URL
		StringBuilder url = new StringBuilder();
		url.append(DirectionConstant.DICDISTRICT_URL)
				.append("?")
				.append("keywords=").append(keywords)
				.append("&")
				.append("subdistrict=").append(level)
				.append("&")
				.append("key=").append(amapKey);

		ResponseEntity<String> forEntity = restTemplate.getForEntity(url.toString(), String.class);
		return forEntity.getBody();
	}
}
