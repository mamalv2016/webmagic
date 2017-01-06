package org.renjie120.common;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.junit.Before;
import org.junit.Test;

import com.renjie120.math.CalcInput;
import com.renjie120.math.CalcStrategyManager;

public class CalcTest {
	CalcInput input;

	@Before
	public void setUp() throws Exception {
		input = new CalcInput();
		input.ben = 900000;
		input.addon = 5000;
		input.fee = 0.084;
		input.month = 60;
		input.year = 5;

	}

	/**
	 * 计算银行默认的存款计算器
	 * 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	@Test
	public void testBankCalc() throws UnsupportedEncodingException, IOException {
		input.calcMethod = 1;
		CalcStrategyManager.saveHtmlFile(input, "d:\\1.html", input.year
				+ "年银行存款");
	}

	@Test
	public void testFuliAndZuijiaCacl() throws UnsupportedEncodingException,
			IOException {
		input.calcMethod = 2;
		CalcStrategyManager.saveHtmlFile(input, "d:\\2.html", input.ben
				+ "复利投资" + input.month + "月"
				+ (input.addon != 0 ? "每月追加" + input.addon + "元" : ""));
	}

	@Test
	public void testDengBenCacl() throws UnsupportedEncodingException,
			IOException {
		input.calcMethod = 3;
		CalcStrategyManager.saveHtmlFile(input, "d:\\3.html", input.ben
				+ "等本金还款" + input.month + "月");
	}

	@Test
	public void testDengBenXiCacl() throws UnsupportedEncodingException,
			IOException {
		input.calcMethod = 4;
		CalcStrategyManager.saveHtmlFile(input, "d:\\4.html", input.ben
				+ "等额本息还款" + input.month + "月");
	}
}
