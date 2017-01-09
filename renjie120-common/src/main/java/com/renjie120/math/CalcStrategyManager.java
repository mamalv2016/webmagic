package com.renjie120.math;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;

import com.google.common.base.Preconditions;
import com.google.common.io.Files;
import com.renjie120.math.calcstrategy.BankCalc;
import com.renjie120.math.calcstrategy.DengBenCacl;
import com.renjie120.math.calcstrategy.DengBenxiCacl;
import com.renjie120.math.calcstrategy.FuliAndZuijiaCacl;
import com.renjie120.math.htmlprint.HtmlPrintCalcWrapper;

public class CalcStrategyManager {
	private static ArrayList<ICaclSumMethod> list;
	// 加载在services文件中配置的初始化class的列表
	// 在services中配置的类，必须要求是有空初始化函数的类！
	static {
		list = new ArrayList<ICaclSumMethod>();
		ServiceLoader<ICaclSumMethod> loader = ServiceLoader
				.load(ICaclSumMethod.class);
		Iterator<ICaclSumMethod> it = loader.iterator();
		while (it.hasNext()) {
			list.add(it.next());
		}
	}

	/**
	 * 找到在service中配置的全部的类，进行测试找到合适的算法，通过accept进行判断是否满足.
	 * 
	 * @param input
	 * @return
	 */
	private static ICaclSumMethod chooseStrategy(CalcInput input) {
		return chooseStrategy(input,true);
	}
	
	/**
	 * 找到在service中配置的全部的类，进行测试找到合适的算法，通过accept进行判断是否满足.
	 * @param input
	 * @param fromConfig 是否从配置文件中获取
	 * @return
	 */
	private static ICaclSumMethod chooseStrategy(CalcInput input,boolean fromConfig) {
		ICaclSumMethod choosedMethod = null;
		if(fromConfig){
			ServiceLoader<ICaclSumMethod> loader = ServiceLoader
					.load(ICaclSumMethod.class); 
			// 选择对应的算法策略
			for (ICaclSumMethod service : loader) {
				if (service.accept(input)) {
					choosedMethod = service;
					break;
				}
			}
		}else{
			if(input.calcMethod==1){
				choosedMethod =new BankCalc();
			}else if(input.calcMethod==3){
				choosedMethod =new DengBenCacl();
			}else if(input.calcMethod==4){
				choosedMethod =new DengBenxiCacl();
			}else if(input.calcMethod==2){
				choosedMethod =new FuliAndZuijiaCacl();
			}
		}
		Preconditions.checkNotNull(choosedMethod, "没有找到合适的算法！");
		return choosedMethod;
	}

	/**
	 * 返回输入参数的计算结果，用html形式表现出来.
	 * 
	 * @param input
	 * @return
	 */
	public static String getHtml(CalcInput input, String title) {
		ICaclSumMethod choosedMethod = chooseStrategy(input);

		HtmlPrintCalcWrapper htmlprint = new HtmlPrintCalcWrapper(choosedMethod);
		return htmlprint.getHtml(input, title);
	}

	/**
	 * 保存html文件到文件系统中.
	 * 
	 * @param input
	 * @param fileName
	 */
	public static void saveHtmlFile(CalcInput input, String fileName,
			String title) {
		ICaclSumMethod choosedMethod = chooseStrategy(input,false);
		HtmlPrintCalcWrapper htmlpnt = new HtmlPrintCalcWrapper(choosedMethod);
		try {
			Files.write(htmlpnt.getHtml(input, title).getBytes("utf-8"),
					new File(fileName));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 计算.
	 * 
	 * @param input
	 * @return
	 */
	public static CalcResult calc(CalcInput input) {
		ICaclSumMethod choosedMethod = chooseStrategy(input);
		return choosedMethod.calc(input);
	}

	/**
	 * 返回收入计划或者还款计划.
	 * 
	 * @param input
	 * @return
	 */
	public static List<? extends IPlan> plan(CalcInput input) {
		ICaclSumMethod choosedMethod = chooseStrategy(input);
		return choosedMethod.plan(input);
	}

}
