package com.renjie120.dto;

import org.nutz.dao.entity.annotation.Column;
import org.nutz.dao.entity.annotation.Id;
import org.nutz.dao.entity.annotation.Table;

/**
 * 统计详情页面对象
 * 
 * @author Administrator
 *
 */
@Table("table_tongji_page_url")
public class StatisPage {
	// 流水号
	@Id
	private Long id;
	// url
	@Column
	private String url;
	@Column
	private String info;
	 

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	// 删除标记
	@Column
	private String deleteFlag;
	// 标题
	@Column
	private String title;
	// 处理标记
	@Column
	private String status;

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
