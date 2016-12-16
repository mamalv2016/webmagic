package com.renjie120.webmagic;

import us.codecraft.webmagic.Page;

public abstract class AbstractPageRequest {
	private Page page = null;

	public AbstractPageRequest(Page page) {
		this.page = page;
	}

	public Page getPage() {
		return page;
	}

	// 获得处理的方式对应的级别
	public abstract int consoleMethod();
}
