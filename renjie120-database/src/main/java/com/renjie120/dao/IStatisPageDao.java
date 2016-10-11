package com.renjie120.dao;

import java.util.List;

import com.renjie120.dto.StatisPage;

public interface IStatisPageDao {
	public void batchInsert(List<StatisPage> pages);

	public void update(StatisPage page);

	public List<StatisPage> query(StatisPage page);
}
