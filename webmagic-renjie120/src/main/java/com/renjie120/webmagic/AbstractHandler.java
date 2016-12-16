package com.renjie120.webmagic;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.renjie120.dao.StatisPageNutzDao;
import com.renjie120.dto.StatisPage;

public abstract class AbstractHandler {
	private AbstractHandler nextHandler = null;

	protected abstract void handle(AbstractPageRequest request);

	public void setNextHandler(AbstractHandler nextHandler) {
		this.nextHandler = nextHandler;
	}

	protected abstract int getHandlerMethod();

	public final void handlerRequest(AbstractPageRequest request) {
		if (request.consoleMethod() == getHandlerMethod()) {
			handle(request);
		} else {
			if (this.nextHandler != null) { 
				this.nextHandler.handlerRequest(request);
			} else {
				System.out.println("当前处理者[" + getHandlerMethod()
						+ "]处理不了,出现异常...");
			}
		}
	}

	/**
	 * 判断数据库中是否存在值指定的url对应的数据，防止多次重复插入
	 * 
	 * @param url
	 * @return
	 */
	protected StatisPage queryPage(String url) {
		StatisPage pageVo = new StatisPage();
		pageVo.setUrl(url);
		StatisPageNutzDao dao = new StatisPageNutzDao();
		List<StatisPage> ans = dao.query(pageVo);
		if (CollectionUtils.isEmpty(ans)) {
			return null;
		} else {
			return ans.get(0);
		}
	}

}
