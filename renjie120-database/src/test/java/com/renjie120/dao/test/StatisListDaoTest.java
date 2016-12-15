package com.renjie120.dao.test;

import java.util.List;

import com.renjie120.dao.StatisListDao;
import com.renjie120.dto.StatisList;

public class StatisListDaoTest {
	public static void main(String[] args) {
		StatisListDao ado = new StatisListDao();

		//
		// List<StatisList> pages = new ArrayList();
		// for(int i=0;i<100;i++){
		// StatisList page = new StatisList();
		// page.setDeleteFlag("1");
		// page.setStatus(StatisPageStatus.NEW);
		// page.setTitle("标题"+i);
		// page.setUrl("ssssssssss"+i);
		// pages.add(page);
		// }
		//
		// ado.batchInsert(pages);
		//
		//
		// StatisList query = new StatisList();
		// query.setTitle("2");
		//
		// List<StatisList> ans = ado.query(query);
		// System.out.println(ans);
		//
		StatisList condition = new StatisList();
		condition.setId(12L);

		List<StatisList> oldObj = ado.query(condition);
		StatisList oldPage = oldObj.get(0);

		oldPage.setTitle("修改之后的值...");

		ado.update(condition, oldPage);
	}
}
