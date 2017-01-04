package com.renjie120.math.htmlprint;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.google.common.base.Preconditions;
import com.renjie120.math.BackPlan;
import com.renjie120.math.CalcInput;
import com.renjie120.math.CalcResult;
import com.renjie120.math.ComeInPlan;
import com.renjie120.math.ICaclSumMethod;
import com.renjie120.math.IPlan;
import com.renjie120.math.tool.MathTool;

/**
 * 打印计算的结果为html页面
 * 
 * @author Administrator
 * 
 */
public class HtmlPrintCalcWrapper {
	private ICaclSumMethod calc;

	public HtmlPrintCalcWrapper(ICaclSumMethod calc) {
		this.calc = calc;
	}

	private <E> ArrayList<E> changeList(List<? extends IPlan> p) {
		ArrayList<E> ans = new ArrayList<E>();
		Iterator<? extends IPlan> it = p.iterator();
		while (it.hasNext()) {
			ans.add((E) it.next());
		}
		return ans;
	}

	private String getBackHtml(CalcResult c, List<BackPlan> allPlans,
			String title) {
		double allBenjin = c.benjin;
		double shouyi = c.lixi;
		double allShouru = MathTool.add(allBenjin, shouyi);
		double shouyilv = 1 + c.rate;
		StringBuilder bui = new StringBuilder();
		bui.append(HtmlTag.htmlTitle(title == null ? "还款计划" : title));
		bui.append(HtmlTag.tableCss());
		bui.append(HtmlTag.table("class='imagetable'"));
		bui.append(HtmlTag.thead(new String[] { "总期数", "本金", "本息", "利息",
				"最終还款率(含本金)" }));
		bui.append(HtmlTag.tr(new String[] { allPlans.size() + "",
				allBenjin + "", allShouru + "", shouyi + "",
				MathTool.multiply(shouyilv, 100) + "%" }));
		bui.append(HtmlTag.thead(new String[] { "期数", "期初资金", "期末资金", "利息",
				"还款率" }));
		Iterator<BackPlan> it = allPlans.iterator();
		while (it.hasNext()) {
			BackPlan p = it.next();
			bui.append(HtmlTag.tr(new String[] { p.periodic + "",
					p.initMoney + "", p.endMoney + "", p.backProfit + "",
					MathTool.multiply(p.rate, 100) + "%" }));
		}
		bui.append(HtmlTag.TABLE_E);
		return bui.toString();
	}

	private String getComeinHtml(CalcResult c, List<ComeInPlan> allPlans,
			String title) {
		double allBenjin = c.benjin;
		double shouyi = c.lixi;
		double allShouru = MathTool.add(allBenjin, shouyi);
		double shouyilv = c.rate;
		StringBuilder bui = new StringBuilder();
		bui.append(HtmlTag.htmlTitle(title == null ? "收入计划" : title));
		bui.append(HtmlTag.tableCss());
		bui.append(HtmlTag.table("class='imagetable'"));
		bui.append(HtmlTag.thead(new String[] { "总期数", "本金", "本息", "利息",
				"最終收益率(仅收益)" }));
		bui.append(HtmlTag.tr(new String[] { allPlans.size() + "",
				allBenjin + "", allShouru + "", shouyi + "",
				MathTool.multiply(shouyilv, 100) + "%" }));
		bui.append(HtmlTag.thead(new String[] { "期数", "期初资金", "期末资金", "利息",
				"收益率" }));
		Iterator<ComeInPlan> it = allPlans.iterator();
		while (it.hasNext()) {
			ComeInPlan p = it.next();
			bui.append(HtmlTag.tr(new String[] { p.periodic + "",
					p.initMoney + "", p.endMoney + "", p.profit + "",
					MathTool.multiply(p.rate, 100) + "%" }));
		}
		bui.append(HtmlTag.TABLE_E);
		return bui.toString();
	}

	public String getHtml(CalcInput input, String title) {
		CalcResult result = calc.calc(input);
		List<? extends IPlan> ans = calc.plan(input);

		Preconditions.checkNotNull(ans);
		Preconditions.checkElementIndex(0, ans.size());

		IPlan p = ans.get(0);
		if (p instanceof ComeInPlan) {
			List<ComeInPlan> allPlans = changeList(ans);
			return getComeinHtml(result, allPlans, title);
		} else if (p instanceof BackPlan) {
			List<BackPlan> allPlans = changeList(ans);
			return getBackHtml(result, allPlans, title);
		} else
			throw new RuntimeException("数据不正确");
	}
}
