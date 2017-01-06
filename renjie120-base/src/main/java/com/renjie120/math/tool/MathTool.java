package com.renjie120.math.tool;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

public class MathTool {
	private static DecimalFormat df = new DecimalFormat("#######.####");

	/**
	 * 减法
	 * 
	 * @param d1
	 * @param d2
	 * @return
	 */
	public static double subtract(double d1, double d2) {
		return subtract(d1, d2, df);
	}

	public static double subtract(double d1, double d2, DecimalFormat f) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(f.format(b1.subtract(b2).doubleValue()));
	}

	/**
	 * 自定义运算法则的除法运算.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 *            精度
	 * @param mode
	 * @return
	 */
	public static double divide(double d1, double d2, int scale,
			RoundingMode mode) {
		return divide(d1, d2, scale, mode, df);
	}

	/**
	 * 自定义运算法则的除法运算.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 * @param mode
	 * @param f
	 * @return
	 */
	public static double divide(double d1, double d2, int scale,
			RoundingMode mode, DecimalFormat f) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(f.format(b1.divide(b2, scale, mode)
				.doubleValue()));
	}

	/**
	 * 四舍五入的除法运算.
	 * 
	 * @param d1
	 * @param d2
	 * @param scale
	 *            精度
	 * @return
	 */
	public static double divide(double d1, double d2, int scale) {
		return divide(d1, d2, scale, df);
	}

	public static double divide(double d1, double d2, int scale, DecimalFormat f) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(f.format(b1.divide(b2, scale,
				RoundingMode.HALF_UP).doubleValue()));
	}

	public static double multiply(double d1, double d2) {
		return multiply(d1, d2, df);
	}

	public static double multiply(double d1, double d2, DecimalFormat f) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(f.format(b1.multiply(b2).doubleValue()));
	}

	public static double add(double d1, double d2, DecimalFormat f) {
		BigDecimal b1 = new BigDecimal(d1);
		BigDecimal b2 = new BigDecimal(d2);
		return Double.parseDouble(f.format(b1.add(b2).doubleValue()));
	}

	public static double add(double d1, double d2) {
		return add(d1, d2, df);
	}
}
