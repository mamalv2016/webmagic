package com.renjie120.dto;

public enum HouseType {
	BELLOW_90("1",1), ABOVE_90_BELLOW_144("2",2), ABOVE_144("3",3);
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

	private HouseType(String cnName, int type) {
		this.cnName = cnName;
		this.type = type;
	}
}
