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
		JSONObject dicDistrictJSONObject = JSONObject.fromObject(dicDistrictResult);
		int status = dicDistrictJSONObject.getInt(DirectionConstant.STATUS);
		if (status != 1) {
			return ResponseResult.fail(CommonStatusEnum.MAP_DISTRICT_EXISTS.getCode(),
					CommonStatusEnum.MAP_DISTRICT_EXISTS.getMessage());
		}
		JSONArray countryArray = dicDistrictJSONObject.getJSONArray(DirectionConstant.DISTRICTS);

		for (int country = 0; country < countryArray.size(); country++) {
			JSONObject countryJSONObject = countryArray.getJSONObject(country);
			String countryAddressCode = countryJSONObject.getString(DirectionConstant.ADCODE);
			String countryAddressName = countryJSONObject.getString(DirectionConstant.NAME);
			String countryParentAddressCode = "0";
			String countryLevel = countryJSONObject.getString(DirectionConstant.LEVEL);
 			insertDicDistrict(countryAddressCode, countryAddressName, countryParentAddressCode, countryLevel);
			if (countryLevel.equals(DirectionConstant.STREET)) {
				continue;
			}
			JSONArray provinceArray = countryJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
			for (int province = 0; province < provinceArray.size(); province++) {
				JSONObject provinceJSONObject = provinceArray.getJSONObject(province);
				String provinceAddressCode = provinceJSONObject.getString(DirectionConstant.ADCODE);
				String provinceAddressName = provinceJSONObject.getString(DirectionConstant.NAME);
				String provinceParentAddressCode = countryAddressCode;
				String provinceLevel = provinceJSONObject.getString(DirectionConstant.LEVEL);
				if (provinceLevel.equals(DirectionConstant.STREET)) {
					continue;
				}
				insertDicDistrict(provinceAddressCode, provinceAddressName, provinceParentAddressCode, provinceLevel);
				JSONArray cityArray = provinceJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
				for (int city = 0; city < cityArray.size(); city++) {
					JSONObject cityJSONObject = cityArray.getJSONObject(city);
					String cityAddressCode = cityJSONObject.getString(DirectionConstant.ADCODE);
					String cityAddressName = cityJSONObject.getString(DirectionConstant.NAME);
					String cityParentAddressCode = provinceAddressCode;
					String cityLevel = cityJSONObject.getString(DirectionConstant.LEVEL);
					if (cityLevel.equals(DirectionConstant.STREET)) {
						continue;
					}
					insertDicDistrict(cityAddressCode, cityAddressName, cityParentAddressCode, cityLevel);
					JSONArray districtArray = cityJSONObject.getJSONArray(DirectionConstant.DISTRICTS);
					for (int district = 0; district < districtArray.size(); district++) {
						JSONObject districtJSONObject =districtArray.getJSONObject(district);
						String districtAddressCode = districtJSONObject.getString(DirectionConstant.ADCODE);
						String districtAddressName = districtJSONObject.getString(DirectionConstant.NAME);
						String districtParentAddressCode = cityAddressCode;
						String districtLevel = districtJSONObject.getString(DirectionConstant.LEVEL);
						if (districtLevel.equals(DirectionConstant.STREET)) {
							continue;
						}
						insertDicDistrict(districtAddressCode, districtAddressName, districtParentAddressCode, districtLevel);
					}
				}
			}
		}

		return ResponseResult.success();
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
