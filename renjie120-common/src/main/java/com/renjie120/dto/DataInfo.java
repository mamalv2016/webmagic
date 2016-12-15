package com.renjie120.dto;

/**
 * 统计数据
 * @author Administrator
 *
 */

public class DataInfo {
	/**
	 * 城市
	 */
	private String city;
	/**
	 * 年份
	 */
	private int year;
	/**
	 * 月份
	 */
	private int month;
	/**
	 * 环比
	 */
	private double huanbi;
	/**
	 * 同比
	 */
	private double tongbi;
	/**
	 * 定基
	 */
	private double dingji;
	/**
	 * 统计类型
	 */
	private StatisType statisType;
	/**
	 * 房屋类型
	 */
	private HouseType houseType;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public double getHuanbi() {
		return huanbi;
	}

	public void setHuanbi(double huanbi) {
		this.huanbi = huanbi;
	}

	public double getTongbi() {
		return tongbi;
	}

	public void setTongbi(double tongbi) {
		this.tongbi = tongbi;
	}

	public double getDingji() {
		return dingji;
	}

	public void setDingji(double dingji) {
		this.dingji = dingji;
	}

	public StatisType getStatisType() {
		return statisType;
	}

	public void setStatisType(StatisType statisType) {
		this.statisType = statisType;
	}

	public HouseType getHouseType() {
		return houseType;
	}

	public void setHouseType(HouseType houseType) {
		this.houseType = houseType;
	}

}
