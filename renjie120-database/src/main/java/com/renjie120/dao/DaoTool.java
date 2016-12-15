package com.renjie120.dao;

import java.util.List;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;
import org.nutz.dao.Dao;

import com.renjie120.db.NutzDao;

public abstract class DaoTool<T> implements IDao<T> { 
	
	@Override
	public void batchInsert(List<T> pages) {
		Dao dao = NutzDao.getInstance().getDao();
		for (T t : pages) {
			dao.insert(t);
		}
	}

	@Override
	public T insert(T obj) {
		Dao dao = NutzDao.getInstance().getDao();
		return dao.insert(obj);
	}

	@Override
	public int delete(T obj) {
		Dao dao = NutzDao.getInstance().getDao();
		return dao.delete(obj);
	}

	abstract Chain makeUpdateValue(T condition);

	abstract Cnd makeUpdateWhere(T condition);

	abstract Cnd makeQueryCondition(T condition);

	@Override
	public int update(T condition, T newPage) {
		Dao dao = NutzDao.getInstance().getDao();
		Chain chain = makeUpdateValue(newPage);

		Cnd cnd = makeUpdateWhere(condition);
 
		return dao.update(condition.getClass(), chain, cnd );
	}

	@Override
	public List<T> query(T page) {
		Dao dao = NutzDao.getInstance().getDao();
		Cnd cond = makeQueryCondition(page); 
		return (List<T>) dao.query(page.getClass(), cond);
	}

}
