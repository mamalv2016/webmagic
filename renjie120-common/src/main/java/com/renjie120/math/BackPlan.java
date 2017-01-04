package com.renjie120.math;

import com.google.common.base.Objects;

/**
 * 还款计划
 * 
 * @author Administrator
 * 
 */
public class BackPlan implements IPlan {
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
	 * 还款利息
	 */
	public double backProfit;

	/**
	 * 还款本金
	 */
	public double backPrincipal;

	/**
	 * 累计还款本息.
	 */
	public double allBack;

	/**
	 * 当期还款本息
	 */
	public double back;

	/**
	 * 还款百分比
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


	public double getBackProfit() {
		return backProfit;
	}


	public void setBackProfit(double backProfit) {
		this.backProfit = backProfit;
	}


	public double getBackPrincipal() {
		return backPrincipal;
	}


	public void setBackPrincipal(double backPrincipal) {
		this.backPrincipal = backPrincipal;
	}


	public double getAllBack() {
		return allBack;
	}


	public void setAllBack(double allBack) {
		this.allBack = allBack;
	}


	public double getBack() {
		return back;
	}


	public void setBack(double back) {
		this.back = back;
	}


	public double getRate() {
		return rate;
	}


	public void setRate(double rate) {
		this.rate = rate;
	}


	public String toString() {
		return Objects.toStringHelper(this).add("期数", periodic)
//				.add("初期本金", initMoney).add("剩余本金", endMoney)
				.add("还款利息", backProfit).add("还款本金", backPrincipal)
				.add("累计还款本息", allBack).add("还款本息", back).add("总还款率", rate)
				.toString();
	}
}
