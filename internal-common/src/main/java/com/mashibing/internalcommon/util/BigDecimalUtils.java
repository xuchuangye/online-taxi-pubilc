package com.mashibing.internalcommon.util;

import java.math.BigDecimal;

/**
 * BigDecimal的工具类
 *
 * @author xcy
 * @date 2022/9/1 - 15:19
 */
public class BigDecimalUtils {

	/**
	 * 加法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double add(double v1, double v2) {
		BigDecimal value1 = BigDecimal.valueOf(v1);
		BigDecimal value2 = BigDecimal.valueOf(v2);
		BigDecimal bigDecimal = value1.add(value2).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	/**
	 * 减法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double sub(double v1, double v2) {
		BigDecimal value1 = BigDecimal.valueOf(v1);
		BigDecimal value2 = BigDecimal.valueOf(v2);
		BigDecimal bigDecimal = value1.subtract(value2).setScale(2, BigDecimal.ROUND_HALF_UP);
		double value = bigDecimal.doubleValue();
		return value < 0 ? 0 : value;
	}

	/**
	 * 乘法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double multiply(double v1, double v2) {
		BigDecimal value1 = BigDecimal.valueOf(v1);
		BigDecimal value2 = BigDecimal.valueOf(v2);
		BigDecimal bigDecimal = value1.multiply(value2).setScale(2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}

	/**
	 * 除法
	 *
	 * @param v1
	 * @param v2
	 * @return
	 */
	public static double divide(double v1, double v2) {
		if (v2 <= 0) {
			throw new IllegalStateException("被除数无效");
		}
		BigDecimal value1 = BigDecimal.valueOf(v1);
		BigDecimal value2 = BigDecimal.valueOf(v2);
		//divide必须要设置roundingMode
		BigDecimal bigDecimal = value1.divide(value2, 2, BigDecimal.ROUND_HALF_UP);
		return bigDecimal.doubleValue();
	}
}
