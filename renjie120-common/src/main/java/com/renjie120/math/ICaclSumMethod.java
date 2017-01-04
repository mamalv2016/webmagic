package com.renjie120.math;

import java.util.List;

/**
 * 计算总额（本金+利息）的接口
 * 
 * @author Administrator
 * 
 */
public interface ICaclSumMethod {
	/**
	 * 设置计算的配置方式.
	 * 
	 * @param calcMethod
	 * @return
	 */
	public boolean accept(CalcInput input);

	/**
	 * 进行计算的公式.
	 * 
	 * @param ben
	 * @param fee
	 * @param month
	 * @param year
	 * @param addOn
	 * @return
	 */
	CalcResult calc(CalcInput input);

	/**
	 * 计算收款计划或者还款计划.
	 * 
	 * @param ben
	 * @param fee
	 * @param month
	 * @param year
	 * @param addOn
	 * @return
	 */
	List<? extends IPlan> plan(CalcInput input);
}
