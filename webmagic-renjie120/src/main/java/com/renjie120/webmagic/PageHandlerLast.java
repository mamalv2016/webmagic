package com.renjie120.webmagic;

import org.apache.commons.beanutils.BeanUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;

import com.renjie120.dao.StatisPageNutzDao;
import com.renjie120.dto.StatisPage;
import com.renjie120.dto.StatisPageStatus;
import com.renjie120.tool.DateTool;

public class PageHandlerLast extends AbstractHandler {

	@Override
	protected void handle(AbstractPageRequest request) {
		Page page = request.getPage();
		Document doc = Jsoup.parse(page.getHtml().get());
		Elements title = doc.select("head title");
		String url = page.getUrl().toString();
		StatisPageNutzDao dao = new StatisPageNutzDao();
		String startTime = DateTool.getStringCurrentDateTime();
		// 先根据url查询数据库中是否已经存在
		StatisPage pageVo = queryPage(url);
		// 不存在，说明之前没有处理过,新建一个记录，保存到page表中，表示是新插入.
		if (pageVo == null) {
			pageVo = new StatisPage();
			pageVo.setDeleteFlag("0");
			pageVo.setStatus(StatisPageStatus.FAILURE.toString());
			pageVo.setUrl(url);
			pageVo.setInfo(startTime + "--解析失败.没有找到对应的table");
			pageVo.setTitle(title.html());
			dao.insert(pageVo);
		} else {
			StatisPage newPageVo = new StatisPage();
			try {
				BeanUtils.copyProperties(newPageVo, pageVo);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
			 
			newPageVo.setStatus(StatisPageStatus.FAILURE.toString());
			newPageVo.setInfo(startTime + "--解析失败.没有找到对应的table");
			dao.update(pageVo, newPageVo);
		} 
	}

	@Override
	protected int getHandlerMethod() {
		// TODO Auto-generated method stub
		return -1;
	}
}
