package com.renjie120.math.calcstrategy;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.renjie120.math.CalcInput;
import com.renjie120.math.CalcResult;
import com.renjie120.math.ComeInPlan;
import com.renjie120.math.ICaclSumMethod;
import com.renjie120.math.tool.MathTool;

/**
 * 复利追加投资计算器. calcMethod ==2
 * 
 * @author Administrator
 * 
 */
public class FuliAndZuijiaCacl implements ICaclSumMethod { 

	/**
	 * 计算复利投资全部的本金（包含最初本金+每期追加投资本金），以及利息
	 * 
	 * @return
	 */
	private double[] calcSum(CalcInput input) {
		double[] ans = new double[2];
		// 全部的本金
		double allCost = input.ben;
		// 全部的金额（本金+收益）
		double allMoney = input.ben;
		// 计算月化收益
		double month_fee = MathTool.divide(input.fee, 12, 4);
		// 循环期数为月份数.
		for (int i = 0; i < input.month; i++) {
			if (i > 0) {
				allCost = MathTool.add(allCost, input.addon);
				allMoney = MathTool.add(allMoney, input.addon);
			}
			allMoney = MathTool.multiply(allMoney, month_fee + 1);
		}
		double allLixi = MathTool.subtract(allMoney, allCost);
		ans[0] = allCost;
		ans[1] = allLixi;
		return ans;
	}

	private void checkArgument(CalcInput input) {
		Preconditions.checkArgument(input.month >= 0, "月份必须大于0");
	}

	@Override
	public boolean accept(CalcInput input) {
		return input.calcMethod == 2;
	}

	@Override
	public CalcResult calc(CalcInput input) {
		checkArgument(input);

		CalcResult result = new CalcResult();
		double[] ans = calcSum(input);
		result.benjin = ans[0];

		result.lixi = ans[1];

		result.rate = MathTool.divide(result.lixi, result.benjin, 5);

		return result;
	}

	@Override
	public List<ComeInPlan> plan(CalcInput input) {
		checkArgument(input);
		List<ComeInPlan> result = Lists.newArrayList();

		// 计算月化收益
		double month_fee = MathTool.divide(input.fee, 12, 4);

		// 初期金额
		double lastBen = input.ben;
		// 总投资本金
		double costBen = input.ben;
		// 总投资收益
		double allProfit = 0;
		for (int i = 0; i < input.month; i++) {
			ComeInPlan p1 = new ComeInPlan();
			p1.periodic = i + 1;
			p1.initMoney = lastBen;
			double profit =  MathTool.multiply(p1.initMoney, month_fee) ; 
			allProfit =  MathTool.add(allProfit,profit);
			p1.profit = allProfit; 
			p1.endMoney = MathTool.add(p1.initMoney, profit);
			p1.rate = MathTool.divide(p1.profit, costBen, 5);

			// 下一期的初期金额在本期的期末金额基础上面添加追加投资金额.
			lastBen = MathTool.add(p1.endMoney, input.addon);

			// 下面计算的是全部的投资的本金
			if (i > 0) {
				costBen = MathTool.add(costBen, input.addon);
			} 
			result.add(p1);
		}
		return result;
	}

}
