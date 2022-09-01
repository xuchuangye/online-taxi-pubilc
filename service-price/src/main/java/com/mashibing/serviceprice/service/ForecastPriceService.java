package com.mashibing.serviceprice.service;

import com.mashibing.internalcommon.constant.CommonStatusEnum;
import com.mashibing.internalcommon.dto.ResponseResult;
import com.mashibing.internalcommon.request.ForecastPriceDTO;
import com.mashibing.internalcommon.response.DirectionResponse;
import com.mashibing.internalcommon.response.ForecastPriceResponse;
import com.mashibing.internalcommon.dto.PriceRule;
import com.mashibing.internalcommon.util.BigDecimalUtils;
import com.mashibing.serviceprice.mapper.PriceRuleMapper;
import com.mashibing.serviceprice.remote.ServiceMapClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xcy
 * @date 2022/8/30 - 17:04
 */
@Service
@Slf4j
public class ForecastPriceService {
	@Autowired
	private ServiceMapClient serviceMapClient;

	@Autowired
	private PriceRuleMapper priceRuleMapper;

	public ResponseResult forecastPrice(String depLongitude, String depLatitude, String destLongitude, String destLatitude) {
		log.info("出发地经度: " + depLongitude);
		log.info("出发地纬度: " + depLatitude);
		log.info("目的地经度: " + destLongitude);
		log.info("目的地纬度: " + destLatitude);

		log.info("调用地图服务，查询距离和时长");
		ForecastPriceDTO forecastPriceDTO = new ForecastPriceDTO();
		forecastPriceDTO.setDepLongitude(depLongitude);
		forecastPriceDTO.setDepLatitude(depLatitude);
		forecastPriceDTO.setDestLongitude(destLongitude);
		forecastPriceDTO.setDestLatitude(destLatitude);

		ResponseResult<DirectionResponse> driving = serviceMapClient.driving(forecastPriceDTO);
		Integer distance = driving.getData().getDistance();
		Integer duration = driving.getData().getDuration();
		log.info("距离：" + distance + "， 时长：" + duration);

		log.info("读取计价规则");
		Map<String, Object> map = new HashMap<>();
		map.put("city_code", "110000");
		map.put("vehicle_type", "1");
		List<PriceRule> priceRules = priceRuleMapper.selectByMap(map);

		if (priceRules == null || priceRules.size() == 0) {
			return ResponseResult.fail(CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getCode(),
					CommonStatusEnum.PRICE_RULE_NOT_EXISTS.getMessage());
		}
		PriceRule priceRule = priceRules.get(0);
		log.info("price rule: " + priceRules.get(0));

		//log.info("读取车辆类型");
		log.info("根据距离、时长、计价规则计算最终预估价格");
		double finalPrice = finalPrice(distance, duration, priceRule);

		ForecastPriceResponse forecastPriceResponse = new ForecastPriceResponse();
		forecastPriceResponse.setPrice(finalPrice);
		return ResponseResult.success(forecastPriceResponse);
	}

	/**
	 * 根据距离、时长以及计价规则计算最终价格
	 *
	 * @param distance  距离
	 * @param duration  时长
	 * @param priceRule 计价规则
	 * @return 返回最终价格
	 */
	private double finalPrice(Integer distance, Integer duration, PriceRule priceRule) {
		//总价格
		double totalPrice = 0.0;
		//起步价
		double startFare = priceRule.getStartFare();
		totalPrice = BigDecimalUtils.add(totalPrice, startFare);

		//起步里程，单位：公里
		int startMile = priceRule.getStartMile();
		//总距离distance单位：米，换算成公里，并且四舍五入保留小数点后两位
		double distances = BigDecimalUtils.divide(distance, 1000);
		//最终需要计算的公里数 = 总距离 - 起步里程
		double mile = BigDecimalUtils.sub(distances, startMile);

		//计程单价
		double unitPricePreMile = priceRule.getUnitPricePreMile();
		//计程价格
		double mileFare = BigDecimalUtils.multiply(unitPricePreMile, mile);

		totalPrice = BigDecimalUtils.add(totalPrice, mileFare);

		//时长，单位：秒
		//需要转换成单位：分钟
		double durationMinute = BigDecimalUtils.divide(duration, 60);

		//计时单价
		double unitPricePreMinute = priceRule.getUnitPricePreMinute();

		double timeFare = BigDecimalUtils.multiply(unitPricePreMinute, durationMinute);

		totalPrice = BigDecimalUtils.add(totalPrice,timeFare);
		return totalPrice;
	}

	public static void main(String[] args) {
		ForecastPriceService forecastPriceService = new ForecastPriceService();
		PriceRule priceRule = new PriceRule();
		priceRule.setUnitPricePreMile(1.8);
		priceRule.setUnitPricePreMinute(0.5);
		priceRule.setStartFare(10.0);
		priceRule.setStartMile(3);

		double totalPrice = forecastPriceService.finalPrice(6500, 1800, priceRule);
		System.out.println(totalPrice);
	}
}
