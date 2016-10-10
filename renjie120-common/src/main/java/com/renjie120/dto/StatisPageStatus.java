package com.renjie120.dto;

public enum StatisPageStatus {
	NEW("新建", 1), READ("已读", 2), SUCCESS("处理成功", 3), FAILURE("处理失败", 3);
	private String cnName;
	private int type;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getCnName() {
		return cnName;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	private StatisPageStatus(String cnName, int type) {
		this.cnName = cnName;
		this.type = type;
	}
}
