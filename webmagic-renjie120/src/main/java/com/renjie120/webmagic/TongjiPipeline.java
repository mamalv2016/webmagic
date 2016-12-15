package com.renjie120.webmagic;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import com.renjie120.dao.StatisListNutzDao;
import com.renjie120.dto.StatisList;
import com.renjie120.dto.StatisPageStatus;

/**
 * Write results in console.<br>
 * Usually used in test.
 *
 * @author code4crafter@gmail.com <br>
 * @since 0.1.0
 */
public class TongjiPipeline implements Pipeline {

	@Override
	public void process(ResultItems resultItems, Task task) {
		for (Map.Entry<String, Object> entry : resultItems.getAll().entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			if ("urls".equals(key)) {
				List<String> urls = (List<String>) value;

				StatisListNutzDao dao = new StatisListNutzDao();

				List<StatisList> pages = new ArrayList();
				for (String url:urls) {
					//防止重复添加
					StatisList page = queryList(url);
					if(page==null){
						page = new StatisList();
						page.setDeleteFlag("0");
						page.setStatus(StatisPageStatus.NEW.toString()); 
						page.setUrl(url);
						pages.add(page);
					}
				}
				
				dao.batchInsert(pages);
			}
		}
	}
	
	private StatisList queryList(String url) {
		StatisList pageVo = new StatisList();
		pageVo.setUrl(url);
		StatisListNutzDao dao = new StatisListNutzDao();
		List<StatisList> ans = dao.query(pageVo);
		if (CollectionUtils.isEmpty(ans)) {
			return null;
		} else {
			return ans.get(0);
		}
	}
}
