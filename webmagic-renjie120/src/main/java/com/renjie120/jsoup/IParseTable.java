package com.renjie120.jsoup;

public interface IParseTable {
	/**
	 * 解析表格
	 */
	public void parseTable();

	/**
	 * 校验当前是正确的table对象
	 */
	public boolean validateTable();

	/**
	 * 保存到数据库
	 */
	public void saveToDb();
	
	/**
	 * 调用nutz的方法保存到数据库
	 */
	public void newSaveToDb();
}
