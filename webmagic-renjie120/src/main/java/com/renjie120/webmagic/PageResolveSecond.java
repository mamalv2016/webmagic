package com.renjie120.webmagic;

import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import us.codecraft.webmagic.Page;

import com.renjie120.common.exception.ExceptionCode;
import com.renjie120.common.exception.ExceptionWrapper;
import com.renjie120.dao.StatisPageNutzDao;
import com.renjie120.dto.StatisPage;
import com.renjie120.dto.StatisPageStatus;
import com.renjie120.jsoup.ParseNewCommercialHouseTable;
import com.renjie120.jsoup.ParseNewHouseSortTable;
import com.renjie120.jsoup.ParseNewHouseTable;
import com.renjie120.jsoup.ParseSecHandlHouseTable;
import com.renjie120.jsoup.ParseSecHandsHouseSortTable;
import com.renjie120.tool.DateTool;

public class PageResolveSecond implements IPageResolve {

	/**
	 * 判断数据库中是否存在值指定的url对应的数据，防止多次重复插入
	 * 
	 * @param url
	 * @return
	 */
	private StatisPage queryPage(String url) {
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
	
	
	@Override
	public void resolve(Page page) {
		Document doc = Jsoup.parse(page.getHtml().get());
		Elements title = doc.select("head title"); 
		String url = page.getUrl().toString();
		StatisPageNutzDao dao = new StatisPageNutzDao();
		String startTime = DateTool.getStringCurrentDateTime();
		// 第二种解析方式. 
		Elements allP = doc.select("table.NOBORDER");
		// 如果有5个表格就进行解析处理.
		if (allP.size() == 5) {
			int tableLen = allP.size(); 
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
			} else {
				String status = pageVo.getStatus();
				// 如果之前已经处理成功了，表明不用再处理了。
				if (status.equals(StatisPageStatus.SUCCESS.toString())) {
					return;
				}
			}

			StatisPage newPageVo = new StatisPage();
			try {
				BeanUtils.copyProperties(newPageVo, pageVo);
			} catch (Exception e1) {
				e1.printStackTrace();
			}

			// 进行解析
			try {
				ParseNewHouseTable t1 = new ParseNewHouseTable(
						allP.get(tableLen - 5));
				ParseNewCommercialHouseTable t2 = new ParseNewCommercialHouseTable(
						allP.get(tableLen - 4));
				ParseSecHandlHouseTable t3 = new ParseSecHandlHouseTable(
						allP.get(tableLen - 3));
				ParseNewHouseSortTable t4 = new ParseNewHouseSortTable(
						allP.get(tableLen - 2));
				ParseSecHandsHouseSortTable t5 = new ParseSecHandsHouseSortTable(
						allP.get(tableLen - 1));

				t1.newSaveToDb();
				t2.newSaveToDb();
				t3.newSaveToDb();
				t4.newSaveToDb();
				t5.newSaveToDb();

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
	}

}
