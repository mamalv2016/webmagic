package com.renjie120.jsoup.dto;

public enum StatisType {
	NEW_HOUSE("1"), NEW_COMMERCIAL_HOUSE("2"), SECOND_HANDS_HOUSE("3"), NEW_HOUSE_SORTED(
			"4"), SECOND_HANDS_HOUSE_SORTED("4");
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

	private StatisType (String cnName) {
		this.cnName = cnName;
	}
}
