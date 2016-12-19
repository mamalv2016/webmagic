package com.renjie120.webmagic;

import org.apache.commons.beanutils.BeanUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;

import com.renjie120.common.exception.ExceptionCode;
import com.renjie120.common.exception.ExceptionWrapper;
import com.renjie120.dao.StatisPageNutzDao;
import com.renjie120.dto.StatisPage;
import com.renjie120.dto.StatisPageStatus;
import com.renjie120.jsoup.ParseNewCommercialHouseTable2;
import com.renjie120.jsoup.ParseNewHouseSortTable2;
import com.renjie120.jsoup.ParseNewHouseTable2;
import com.renjie120.jsoup.ParseSecHandlHouseTable2;
import com.renjie120.jsoup.ParseSecHandsHouseSortTable2;
import com.renjie120.tool.DateTool;

public class PageHandler5 extends AbstractHandler {

	@Override
	protected void handle(AbstractPageRequest request) {
		Page page = request.getPage();
		Document doc = Jsoup.parse(page.getHtml().get());
		Elements title = doc.select("head title");
		String url = page.getUrl().toString();
		String _t = title.html(); 
		StatisPageNutzDao dao = new StatisPageNutzDao();
		String startTime = DateTool.getStringCurrentDateTime();
		// 第一种解析方式.
		Elements allP = doc.select("table.NOBORDER"); 
		// 先根据url查询数据库中是否已经存在
		StatisPage pageVo = queryPage(url);
		// 不存在，说明之前没有处理过,新建一个记录，保存到page表中，表示是新插入.
		if (pageVo == null) {
			pageVo = new StatisPage();
			pageVo.setDeleteFlag("0");
			pageVo.setStatus(StatisPageStatus.NEW.toString());
			pageVo.setUrl(url);
			pageVo.setTitle(title.html());
			dao.insert(pageVo);
		} 

		StatisPage newPageVo = new StatisPage();
		try {
			BeanUtils.copyProperties(newPageVo, pageVo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		// 进行解析
		try {
			ParseNewHouseTable2 t1 = new ParseNewHouseTable2(
					allP.get(0),_t);
			ParseNewCommercialHouseTable2 t2 = new ParseNewCommercialHouseTable2(
					allP.get(1),_t);
			ParseSecHandlHouseTable2 t3 = new ParseSecHandlHouseTable2(
					allP.get(2),_t);
			ParseNewHouseSortTable2 t4 = new ParseNewHouseSortTable2(
					allP.get(3),_t);
			ParseNewHouseSortTable2 t5 = new ParseNewHouseSortTable2(
					allP.get(4),_t);
			ParseSecHandsHouseSortTable2 t6 = new ParseSecHandsHouseSortTable2(
					allP.get(5),_t);
			ParseSecHandsHouseSortTable2 t7 = new ParseSecHandsHouseSortTable2(
					allP.get(6),_t);

			t1.newSaveToDb();
			t2.newSaveToDb();
			t3.newSaveToDb();
			t4.newSaveToDb();
			t5.newSaveToDb();
			t6.newSaveToDb();
			t7.newSaveToDb();

			// 解析完之后，更新解析状态为成功..
			newPageVo.setStatus(StatisPageStatus.SUCCESS.toString());
			newPageVo.setInfo(startTime + "--成功操作.结束时间："
					+ DateTool.getStringCurrentDateTime());
			dao.update(pageVo, newPageVo);

		} catch (Exception e) {
			// 出现异常，就更新状态为失败
			newPageVo.setStatus(StatisPageStatus.FAILURE.toString());
			newPageVo.setInfo(startTime + "--解析失败.原因：" + e.getMessage());
			dao.update(pageVo, newPageVo);

			e.printStackTrace();

			throw new ExceptionWrapper(e.getMessage(),
					ExceptionCode.URL_PARSE_ERROR, url);
		}
	} 
	@Override
	protected int getHandlerMethod() {
		// TODO Auto-generated method stub
		return ConsoleMethodConstants.METHOD_5;
	}
}
