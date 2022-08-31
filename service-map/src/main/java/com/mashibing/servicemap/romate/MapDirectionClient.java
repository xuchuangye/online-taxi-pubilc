package com.mashibing.servicemap.romate;

import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.response.DirectionResponse;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author xcy
 * @date 2022/8/31 - 10:42
 */
@Service
//@Slf4j
public class MapDirectionClient {

	/**
	 * 获取application.yaml配置文件中的amap.key属性以及属性值
	 */
	@Value("${amap.key}")
	private String amapKey;

	@Autowired
	private RestTemplate restTemplate;

	/**
	 * @param depLongitude  出发地的经度
	 * @param depLatitude   出发地的纬度
	 * @param destLongitude 目的地的经度
	 * @param destLatitude  目的地的纬度
	 * @return 获取距离和时长
	 */
	public DirectionResponse direction(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
		//组装请求调用URL
		StringBuilder urlBuild = getURL(depLongitude, depLatitude, destLongitude, destLatitude);
		//log.info("URL: " + urlBuild.toString());

		//调用高德接口
		ResponseEntity<String> directionForEntity = restTemplate.getForEntity(urlBuild.toString(), String.class);
		String directionString = directionForEntity.getBody();
		//log.info("高德地图，路径规划，数据信息：" + directionString);

		//解析接口
		return parseDirectionString(directionString);
	}

	/**
	 * 组装地图路线URL
	 *
	 * @param depLongitude  出发地的经度
	 * @param depLatitude   出发地的纬度
	 * @param destLongitude 目的地的经度
	 * @param destLatitude  目的地的纬度
	 * @return 返回组装之后的地图路线URL
	 */
	private StringBuilder getURL(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
		StringBuilder urlBuild = new StringBuilder();
		urlBuild.append(DirectionConstant.DIRECTION_URL)
				.append("?")
				.append("origin=").append(depLongitude).append(",").append(depLatitude)
				.append("&")
				.append("destination=").append(destLongitude).append(",").append(destLatitude)
				.append("&")
				.append("extensions=all")
				.append("&")
				.append("output=json")
				.append("&")
				.append("key=").append(amapKey)
		;
		return urlBuild;
	}

	/**
	 * 解析JSON字符串，获取distance和duration，并且封装到DirectionResponse
	 *
	 * @param directionString JSON字符串
	 * @return 获取distance和duration，并且封装到DirectionResponse
	 */
	private DirectionResponse parseDirectionString(String directionString) {
		DirectionResponse directionResponse = null;
		if (directionString != null) {
			try {
				JSONObject result = JSONObject.fromObject(directionString);
				//JSON的status字段
				int status = result.getInt(DirectionConstant.STATUS);
				//status == 0表示请求失败
				//status == 1表示请求成功
				if (status == 1) {
					//JSON的route字段
					if (result.has(DirectionConstant.ROUTE)) {
						JSONObject route = result.getJSONObject(DirectionConstant.ROUTE);
						//route的paths字段
						if (route.has(DirectionConstant.PATHS)) {
							JSONArray paths = route.getJSONArray(DirectionConstant.PATHS);
							//paths的第一个对象firstPath
							JSONObject firstPath = paths.getJSONObject(0);
							if (firstPath != null) {
								directionResponse = new DirectionResponse();
								int distance = 0;
								int duration = 0;
								//firstPath的distance字段
								if (firstPath.has(DirectionConstant.DISTANCE)) {
									distance = firstPath.getInt(DirectionConstant.DISTANCE);
								}
								//firstPath的duration字段
								if (firstPath.has(DirectionConstant.DURATION)) {
									duration = firstPath.getInt(DirectionConstant.DURATION);
								}
								directionResponse.setDistance(distance);
								directionResponse.setDuration(duration);
							}
						}
					}
				}
			}catch (Exception e) {

			}
		}
		return directionResponse;
	}
}
