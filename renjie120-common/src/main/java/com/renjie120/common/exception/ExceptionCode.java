package com.renjie120.common.exception;

public class ExceptionCode {

	public final static ExceptionCode UNDEF_ERROR = new ExceptionCode("500",
			"不好意思，系统繁忙，请稍后再试！");
	public final static ExceptionCode REMOTE_INVOKE = new ExceptionCode("0001",
			"【%s】接口调用异常，接口地址【%s】！");
	public final static ExceptionCode UNDEF_REMOTE_INVOKE = new ExceptionCode(
			"0012", "接口调用异常，接口地址【%s】！%s");
	public final static ExceptionCode NOT_CUSTOMER_ORDER_ERROR = new ExceptionCode(
			"2002", "禁止非会员下单,请先登录!");

	/********************************************* 数据库ExceptionCode *******************************************************************/
	public final static ExceptionCode DB_FAIL = new ExceptionCode("2006",
			"查询数据库出现异常");

	public final static ExceptionCode DOM_NOT_FOUND = new ExceptionCode("3001",
			"待解析dom元素未找到");

	public final static ExceptionCode URL_PARSE_ERROR = new ExceptionCode(
			"4001", "解析[%s]的连接出现异常");

	public final static ExceptionCode DOM_ISNOT_TABLE = new ExceptionCode(
			"3002", "待解析对象不是table");

	/********************************************* 机酒动态打包ExceptionCode *******************************************************************/
	public String clientErrMessage(Object... vars) {
		return null == this.clientMessageFmt ? null : String.format(
				this.clientMessageFmt, vars);
	}

	private String clientMessageFmt;

	public String getClientMessageFmt() {
		return clientMessageFmt;
	}

	public void setClientMessageFmt(String clientMessageFmt) {
		this.clientMessageFmt = clientMessageFmt;
	}

	public final static ExceptionCode END_TAG = new ExceptionCode("-1",
			"异常枚举code结束TAG(非正常使用)");

	public ExceptionCode(String code, String messageFmt) {
		this.code = code;
		this.messageFmt = messageFmt;
	}

	/** 编码 */
	private String code;

	/** 消息格式字符串 */
	private String messageFmt;

	public String errMessage(Object... vars) {
		return String.format(this.messageFmt, vars);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessageFmt() {
		return messageFmt;
	}

	public void setMessageFmt(String messageFmt) {
		this.messageFmt = messageFmt;
	}

}