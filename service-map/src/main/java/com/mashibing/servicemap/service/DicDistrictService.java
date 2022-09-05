package com.mashibing.servicemap.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.constant.DirectionConstant;
import com.mashibing.internalcommon.dto.DicDistrict;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.servicemap.mapper.DicDistrictMapper;
import com.mashibing.servicemap.romate.MapDicDistrictClient;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author xcy
 * @date 2022/9/2 - 8:26
 */
@Service
public class DicDistrictService {

	@Autowired
	private MapDicDistrictClient mapDicDistrictClient;

	@Autowired
	private DicDistrictMapper dicDistrictMapper;


	public ResponseResult initDicDistrict(String keywords) {
		//组装地图字典表的URL
		String dicDistrictResult = mapDicDistrictClient.dicDistrict(keywords);
		System.out.println(dicDistrictResult);
		//解析URL
		//1.获取JSON对象
		JSONObject dicDistrictJSONObject = JSONObject.fromObject(dicDistrictResult);
		int status = dicDistrictJSONObject.getInt(DirectionConstant.STATUS);
		if (status != 1) {
			return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_EXISTS.getCode(),
					CommonStatusEnum.MAP_DISTRICT_EXISTS.getMessage());
		}
		//2.根据JSON对象中的districts属性，获取国家的数组
		parseUrl(dicDistrictJSONObject);

		return ResponseResult.success();
	}

	/**
	 * 解析URL
	 *
	 * @param dicDistrictJSONObject
	 */
	private void parseUrl(JSONObject dicDistrictJSONObject) {
		JSONArray countryArray = dicDistrictJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
		//遍历数组
		for (int country = 0; country < countryArray.size(); country++) {
			//国家级对象
			JSONObject countryJSONObject = countryArray.getJSONObject(country);
			//国家地址编码
			String countryAddressCode = countryJSONObject.getString(DirectionConstant.ADCODE);
			//国家地址名称
			String countryAddressName = countryJSONObject.getString(DirectionConstant.NAME);
			//国家上一级地址编码
			String countryParentAddressCode = "0";
			//国家的级别
			String countryLevel = countryJSONObject.getString(DirectionConstant.LEVEL);
			//插入到数据库
			insertDicDistrict(countryAddressCode, countryAddressName, countryParentAddressCode, countryLevel);
			//忽略街道级别
			if (countryLevel.equals(DirectionConstant.STREET)) {
				continue;
			}
			//根据国家对象中的districts属性，获取省级的数组
			JSONArray provinceArray = countryJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
			for (int province = 0; province < provinceArray.size(); province++) {
				//省级对象
				JSONObject provinceJSONObject = provinceArray.getJSONObject(province);
				//省级地址编码
				String provinceAddressCode = provinceJSONObject.getString(DirectionConstant.ADCODE);
				//省级地址名称
				String provinceAddressName = provinceJSONObject.getString(DirectionConstant.NAME);
				//省级上一级地址编码
				String provinceParentAddressCode = countryAddressCode;
				//省级级别
				String provinceLevel = provinceJSONObject.getString(DirectionConstant.LEVEL);
				//忽略街道级别
				if (provinceLevel.equals(DirectionConstant.STREET)) {
					continue;
				}
				insertDicDistrict(provinceAddressCode, provinceAddressName, provinceParentAddressCode, provinceLevel);
				//根据省级对象中的districts属性，获取市级的数组
				JSONArray cityArray = provinceJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
				for (int city = 0; city < cityArray.size(); city++) {
					//市级对象
					JSONObject cityJSONObject = cityArray.getJSONObject(city);
					//市级地址编码
					String cityAddressCode = cityJSONObject.getString(DirectionConstant.ADCODE);
					//市级地址名称
					String cityAddressName = cityJSONObject.getString(DirectionConstant.NAME);
					//市级上一级地址编码
					String cityParentAddressCode = provinceAddressCode;
					//市级级别
					String cityLevel = cityJSONObject.getString(DirectionConstant.LEVEL);
					//忽略街道级别
					if (cityLevel.equals(DirectionConstant.STREET)) {
						continue;
					}
					insertDicDistrict(cityAddressCode, cityAddressName, cityParentAddressCode, cityLevel);
					//根据市级对象中的districts属性，获取区级的数组
					JSONArray districtArray = cityJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
					for (int district = 0; district < districtArray.size(); district++) {
						//区级的对象
						JSONObject districtJSONObject = districtArray.getJSONObject(district);
						//区级地址编码
						String districtAddressCode = districtJSONObject.getString(DirectionConstant.ADCODE);
						//区级地址名称
						String districtAddressName = districtJSONObject.getString(DirectionConstant.NAME);
						//区级上一级地址编码
						String districtParentAddressCode = cityAddressCode;
						//区级级别
						String districtLevel = districtJSONObject.getString(DirectionConstant.LEVEL);
						//忽略街道级别
						if (districtLevel.equals(DirectionConstant.STREET)) {
							continue;
						}
						insertDicDistrict(districtAddressCode, districtAddressName, districtParentAddressCode, districtLevel);
					}
				}
			}
		}
	}

	/**
	 * 插入DicDistrict到数据库
	 *
	 * @param addressCode       地区编码
	 * @param addressName       地区名称
	 * @param parentAddressCode 上一级地区编码
	 * @param level             级别
	 */
	private void insertDicDistrict(String addressCode, String addressName, String parentAddressCode, String level) {
		DicDistrict dicDistrict = new DicDistrict();
		dicDistrict.setAddressCode(addressCode);
		dicDistrict.setAddressName(addressName);
		int levelInt = getLevelInt(level);
		dicDistrict.setLevel(levelInt);
		dicDistrict.setParentAddressCode(parentAddressCode);

		dicDistrictMapper.insert(dicDistrict);
	}

	/**
	 * 根据级别（国家、省、市、地区）返回对应的level
	 *
	 * @param level
	 * @return
	 */
	private int getLevelInt(String level) {
		int levelInt = 0;
		switch (level.trim()) {
			case "country":
				return levelInt;
			case "province":
				levelInt = 1;
				break;
			case "city":
				levelInt = 2;
				break;
			case "district":
				levelInt = 3;
				break;
		}
		return levelInt;
	}
}
