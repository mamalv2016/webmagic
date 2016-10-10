package com.renjie120.dto;

public enum StatisPageStatus {
	NEW("新建", 1), READ("已读", 2), SUCCESS("处理成功", 3), FAILURE("处理失败", 3), NONE(
			"默认", 0);
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

	public static String getNameByType(int type) {
		if (type == 1) {
			return "NEW";
		} else if (type == 2) {
			return "READ";
		} else if (type == 3) {
			return "SUCCESS";
		} else if (type == 4) {
			return "FAILURE";
		}
		return "NONE";
	}
}
