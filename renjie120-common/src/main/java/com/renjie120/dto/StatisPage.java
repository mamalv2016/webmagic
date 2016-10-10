package com.renjie120.dto;

public class StatisPage {
	// 流水号
	private long id;
	// url
	private String url;
	// 删除标记
	private String deleteFlag;
	// 标题
	private String title;
	// 处理标记
	private StatisPageStatus status;

	public long getId() {
		return id;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setId(long id) {
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

	public StatisPageStatus getStatus() {
		return status;
	}

	public void setStatus(StatisPageStatus status) {
		this.status = status;
	}

}
