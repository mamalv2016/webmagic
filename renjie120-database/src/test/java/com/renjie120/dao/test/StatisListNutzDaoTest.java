package com.renjie120.dao.test;

import java.util.ArrayList;
import java.util.List;

import com.renjie120.dao.StatisListNutzDao;
import com.renjie120.dto.StatisList;

public class StatisListNutzDaoTest {
	public static void main(String[] args) {
		StatisListNutzDao ado = new StatisListNutzDao();

		List<StatisList> pages = new ArrayList();
		for (int i = 0; i < 100; i++) {
			StatisList page = new StatisList();
			page.setDeleteFlag("1");
			page.setStatus("3");
			page.setTitle("标题" + i);
			page.setUrl("ssssssssss" + i);
			pages.add(page);
		}

		ado.batchInsert(pages);

		StatisList query = new StatisList();
		query.setTitle("2");

		List<StatisList> ans = ado.query(query);
		System.out.println(ans);

		// StatisList condition = new StatisList();
		// condition.setId(13L);
		//
		// List<StatisList> oldObj = ado.query(condition);
		// StatisList oldPage = oldObj.get(0);
		//
		// oldPage.setTitle("12112之后的值...");
		//
		// ado.update(condition, oldPage);
	}
}
