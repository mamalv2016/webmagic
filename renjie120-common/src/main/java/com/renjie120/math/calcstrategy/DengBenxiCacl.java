package com.renjie120.math.calcstrategy;

import java.text.DecimalFormat;
import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.renjie120.math.BackPlan;
import com.renjie120.math.CalcInput;
import com.renjie120.math.CalcResult;
import com.renjie120.math.ICaclSumMethod;
import com.renjie120.math.tool.MathTool;

/**
 * 等本息还贷投资计算器. calcMethod ==4
 * 
 * @author Administrator
 * 
 */
public class DengBenxiCacl implements ICaclSumMethod { 

	private double lixi(CalcInput input) {
		return MathTool.subtract(
				MathTool.multiply(everyMonthBack(input), input.month), input.ben);
	}

	private void checkArgument(CalcInput input) {
		Preconditions.checkArgument(input.month >= 0, "月份必须大于0");
	}

	@Override
	public boolean accept(CalcInput input) {
		return input.calcMethod == 4;
	}

	@Override
	public CalcResult calc(CalcInput input) {
		checkArgument(input);
		CalcResult result = new CalcResult();

		result.benjin = input.ben;

		result.lixi = lixi(input);

		result.rate = MathTool.divide(result.lixi, result.benjin, 5);

		return result;
	}

	/**
	 * 计算每月的还款额 设贷款总额为A，银行月利率为β，总期数为m（个月），月还款额设为X， 最后  X = Aβ(1+β)^m/((1+β)^m -1
	 * ) 推导过程：http://www.meng800.com/article/content-6-2998-1.html
	 * 
	 * @return
	 */
	private double everyMonthBack(CalcInput input) {
		DecimalFormat dd = new DecimalFormat("#######.####");
		// （还款月数+1）×贷款额×月利率/2+ 贷款额
		// 计算月化收益
		double month_fee = MathTool.divide(input.fee, 12.0, 4);
		double temp = Double.parseDouble(dd.format(Math.pow(1 + month_fee,
				input.month)));
		double temp1 = MathTool.multiply(
				MathTool.multiply(input.ben, month_fee, dd), temp, dd);
		double temp2 = MathTool.subtract(temp, 1, dd);
		double d = MathTool.divide(temp1, temp2, 2, dd);
		return d;
	}

	@Override
	public List<BackPlan> plan(CalcInput input) {
		checkArgument(input);
		List<BackPlan> result = Lists.newArrayList();
		// 计算月化收益
		double month_fee = MathTool.divide(input.fee, 12, 4);

		double lastBen = input.ben;
		// 每期还款本息和.
		double everyBack = everyMonthBack(input);
		for (int i = 0; i < input.month; i++) {
			BackPlan p1 = new BackPlan();
			p1.periodic = i + 1;
			p1.initMoney = lastBen;
			// 当月偿还的利息
			p1.backProfit = MathTool.multiply(p1.initMoney, month_fee);
			p1.back = everyBack;
			// 当月偿还的本金
			p1.backPrincipal = MathTool.subtract(p1.back, p1.backProfit);
			// 期末本金
			p1.endMoney = MathTool.subtract(p1.initMoney, p1.backPrincipal);
			// 累计还款本息
			p1.allBack = MathTool.multiply(everyBack, i + 1);
			// 累计还款比率
			p1.rate = MathTool.divide(p1.allBack, input.ben, 3);

			lastBen = p1.endMoney;
			result.add(p1);
		}
		return result;
	}
}
