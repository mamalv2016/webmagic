package com.renjie120.math.calcstrategy;

import java.util.List;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.renjie120.math.BackPlan;
import com.renjie120.math.CalcInput;
import com.renjie120.math.CalcResult;
import com.renjie120.math.ICaclSumMethod;
import com.renjie120.math.tool.MathTool;

/**
 * 等本金还贷投资计算器. 计算返还的全部本息之和.
 *  calcMethod ==3
 * 
 * @author Administrator
 * 
 */
public class DengBenCacl implements ICaclSumMethod {  

	private void checkArgument(CalcInput input) {
		Preconditions.checkArgument(input.month >= 0, "月份必须大于0");
	}

	/**
	 * 计算总共的还款利息.
	 * 
	 * @return
	 */
	public double lixi(CalcInput input) {
		// 总利息=〔(总贷款额÷还款月数+总贷款额×月利率)+总贷款额÷还款月数×(1+月利率)〕÷2×还款月数-总贷款额
		// 计算月化收益
		double month_fee = MathTool.divide(input.fee, 12, 4);
		double temp3 = MathTool.add(MathTool.divide(input.ben, input.month, 5),
				MathTool.multiply(input.ben, month_fee));
		double temp4 = MathTool.multiply(
				MathTool.divide(input.ben, input.month, 5), 1 + month_fee);
		double temp5 = MathTool.add(temp3, temp4);
		return MathTool.subtract(
				MathTool.multiply(MathTool.divide(temp5, 2, 5), input.month),
				input.ben);
	}

	@Override
	public boolean accept(CalcInput input) {
		return input.calcMethod == 3;
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

	@Override
	public List<BackPlan> plan(CalcInput input) {
		checkArgument(input);
		List<BackPlan> result = Lists.newArrayList();
		// 计算月化收益
		double month_fee = MathTool.divide(input.fee, 12, 4);

		double lastBen = input.ben;
		double allBack = 0;
		// 每期偿还的本金
		double everyCostBen = MathTool.divide(input.ben, input.month, 5);
		for (int i = 0; i < input.month; i++) {
			BackPlan p1 = new BackPlan();
			p1.periodic = i + 1;
			p1.initMoney = lastBen;
			// 当月偿还的利息
			p1.backProfit = MathTool.multiply(p1.initMoney, month_fee);
			// 当月偿还的本金
			p1.backPrincipal = everyCostBen;
			// 期末本金
			p1.endMoney = MathTool.subtract(p1.initMoney, p1.backPrincipal);
			p1.back = MathTool.add(p1.backPrincipal, p1.backProfit);
			// 累计还款本息
			p1.allBack = MathTool.add(p1.backPrincipal,
					MathTool.add(p1.backProfit, allBack));
			// 累计还款比率
			p1.rate = MathTool.divide(p1.allBack, input.ben, 3);

			lastBen = p1.endMoney;
			allBack = p1.allBack;
			result.add(p1);
		}
		return result;
	}

}
