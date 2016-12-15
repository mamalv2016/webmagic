package com.renjie120.dao;

import java.util.List;

public interface IDao<T> {
	public void batchInsert(List<T> pages);

	public T insert(T obj);

	public int delete(T obj);

	public int update(T condition, T newPage);

	public List<T> query(T page);
}
