package com.renjie120.math;

import com.google.common.base.Objects;

/**
 * 收款计划
 * 
 * @author Administrator
 * 
 */
public class ComeInPlan implements IPlan {
	/**
	 * 周期数
	 */
	public int periodic;

	/**
	 * 初期金额
	 */
	public double initMoney;

	/**
	 * 期末金额
	 */
	public double endMoney;

	/**
	 * 收益金额
	 */
	public double profit;

	/**
	 * 收益率(对于还款就不计算该值。)
	 */
	public double rate;

	public int getPeriodic() {
		return periodic;
	}

	public void setPeriodic(int periodic) {
		this.periodic = periodic;
	}

	public double getInitMoney() {
		return initMoney;
	}

	public void setInitMoney(double initMoney) {
		this.initMoney = initMoney;
	}

	public double getEndMoney() {
		return endMoney;
	}

	public void setEndMoney(double endMoney) {
		this.endMoney = endMoney;
	}

	public double getProfit() {
		return profit;
	}

	public void setProfit(double profit) {
		this.profit = profit;
	}

	public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public String toString() {
		return Objects.toStringHelper(this).add("期数", periodic)
				.add("初期金额", initMoney).add("期末金额", endMoney).add("收益", profit)
				.add("收益率", rate).toString();
	}
}
