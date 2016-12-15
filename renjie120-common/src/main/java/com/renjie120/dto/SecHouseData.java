package com.renjie120.dto;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 新商品房数据
 * @author Administrator
 *
 */
@Table("table_sec_house")
public class SecHouseData {
	@Id
	private Long id;
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/**
	 * 城市
	 */
	@Column
	private String city;
	/**
	 * 年份
	 */
	@Column
	private int year;
	/**
	 * 月份
	 */
	@Column
	private int month;
	/**
	 * 环比
	 */
	@Column
	private double huanbi;
	/**
	 * 同比
	 */
	@Column
	private double tongbi;
	/**
	 * 定基
	 */
	@Column
	private double dingji; 

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
}
