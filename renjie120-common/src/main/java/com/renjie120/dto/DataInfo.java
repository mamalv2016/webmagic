package com.renjie120.dto;

public class DataInfo {
	private String city;
	private int year;
	private int month;
	private double huanbi;
	private double tongbi;
	private double dingji;
	private StatisType statisType;
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
