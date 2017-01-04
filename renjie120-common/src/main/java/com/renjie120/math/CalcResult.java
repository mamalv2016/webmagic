package com.renjie120.math;

import com.google.common.base.Objects;

public class CalcResult {
	/**
	 * 全部本金
	 */
	public double benjin;
	/**
	 * 全部利息
	 */
	public double lixi;

	/**
	 * 全部实际的收益率
	 */
	public double rate;

	public String toString() {
		return Objects.toStringHelper(this).add("本金", benjin).add("利息", lixi)
				.add("收益率", rate).toString();
	}
}
