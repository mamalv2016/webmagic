package com.renjie120.db;

import javax.sql.DataSource;

import org.nutz.dao.Dao;
import org.nutz.dao.impl.NutDao;
import org.nutz.ioc.Ioc;
import org.nutz.ioc.impl.NutIoc;
import org.nutz.ioc.loader.json.JsonLoader;

import com.renjie120.dto.StatisList;
import com.renjie120.dto.StatisPageStatus;

public class NutzDao {
	private NutzDao() {

	}

	// 2.单例缓存者，惰性初始化，第一次使用时初始化
	private static class InstanceHolder {
		private static final NutzDao INSTANCE = new NutzDao();
	}

	// 3.提供全局访问点
	public static NutzDao getInstance() {
		return InstanceHolder.INSTANCE;
	}

	public Dao getDao() {
		Ioc ioc = new NutIoc(new JsonLoader("dao.js"));
		DataSource ds = ioc.get(DataSource.class);
		Dao dao = new NutDao(ds);
		return dao;
	}

	public static void main(String[] args){
		Dao dao = NutzDao.getInstance().getDao();
		StatisList p = new StatisList();
		p.setDeleteFlag("1");
		p.setTitle("test");
		p.setUrl("11111111");
		p.setStatus(StatisPageStatus.FAILURE.toString()); 
		dao.insert(p);
		System.out.println(p.getId());
	}
}
