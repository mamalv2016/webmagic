package com.renjie120.dao;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;

import com.alibaba.druid.util.StringUtils;
import com.renjie120.dto.NewHouseSortData;

public class NewHouseSortDataNutzDao extends DaoTool<NewHouseSortData> { 
	@Override
	Chain makeUpdateValue(NewHouseSortData condition) {
		Chain ans = null;
		if (condition != null) {
			if (!StringUtils.isEmpty(condition.getCity())) {
				ans = (ans == null ? Chain.make("city",
						condition.getCity()) : ans.add("city",
						condition.getCity()));
			}
			if (condition.getDingji()>0) {
				ans = (ans == null ? Chain
						.make("dingji", condition.getDingji()) : ans.add(
						"dingji", condition.getDingji()));
			}
			if (condition.getHuanbi()>0) {
				ans = (ans == null ? Chain.make("huanbi", condition.getHuanbi())
						: ans.add("huanbi", condition.getHuanbi()));
			}
			if (condition.getMonth()>0) {
				ans = (ans == null ? Chain.make("month", condition.getMonth())
						: ans.add("month", condition.getMonth()));
			}
			if (condition.getYear()>0) {
				ans = (ans == null ? Chain.make("year", condition.getYear())
						: ans.add("year", condition.getYear()));
			}
			if (condition.getTongbi()>0) {
				ans = (ans == null ? Chain.make("tongbi", condition.getTongbi())
						: ans.add("tongbi", condition.getTongbi()));
			}
		}
		return ans;
	}

	@Override
	Cnd makeUpdateWhere(NewHouseSortData condition) {
		Cnd c = null;
		if (condition != null) {
			if (condition.getId() != null && condition.getId() > 0) {
				c = (c == null ? Cnd.where("id", "=", condition.getId()) : c
						.and("id", "=", condition.getId()));
			}
			if (!StringUtils.isEmpty(condition.getCity())) {
				c = (c == null ? Cnd.where("city", "LIKE", "%"+condition.getCity()+"%") : c
						.and("city", "LIKE", "%"+condition.getCity()+"%"));
			}
			if (condition.getDingji()>0) {
				c = (c == null ? Cnd.where("dingji", "=", condition.getDingji()) : c
						.and("dingji", "=", condition.getDingji()));
			}
			if (condition.getHuanbi()>0) {
				c = (c == null ? Cnd.where("huanbi", "=", condition.getHuanbi()) : c
						.and("huanbi", "=", condition.getHuanbi()));
			}
			if (condition.getMonth()>0) {
				c = (c == null ? Cnd.where("month", "=", condition.getMonth()) : c
						.and("month", "=", condition.getMonth()));
			}
			if (condition.getYear()>0) {
				c = (c == null ? Cnd.where("year", "=", condition.getYear()) : c
						.and("year", "=", condition.getYear()));
			}
			if (condition.getTongbi()>0) {
				c = (c == null ? Cnd.where("tongbi", "=", condition.getTongbi()) : c
						.and("tongbi", "=", condition.getTongbi()));
			}
		}
		return c;
	}

	@Override
	Cnd makeQueryCondition(NewHouseSortData condition) {
		Cnd c = null;
		if (condition != null) {
			if (condition.getId() != null && condition.getId() > 0) {
				c = (c == null ? Cnd.where("id", "=", condition.getId()) : c
						.and("id", "=", condition.getId()));
			}
			if (!StringUtils.isEmpty(condition.getCity())) {
				c = (c == null ? Cnd.where("city", "LIKE", "%"+condition.getCity()+"%") : c
						.and("city", "LIKE", "%"+condition.getCity()+"%"));
			}
			if (condition.getDingji()>0) {
				c = (c == null ? Cnd.where("dingji", "=", condition.getDingji()) : c
						.and("dingji", "=", condition.getDingji()));
			}
			if (condition.getHuanbi()>0) {
				c = (c == null ? Cnd.where("huanbi", "=", condition.getHuanbi()) : c
						.and("huanbi", "=", condition.getHuanbi()));
			}
			if (condition.getMonth()>0) {
				c = (c == null ? Cnd.where("month", "=", condition.getMonth()) : c
						.and("month", "=", condition.getMonth()));
			}
			if (condition.getYear()>0) {
				c = (c == null ? Cnd.where("year", "=", condition.getYear()) : c
						.and("year", "=", condition.getYear()));
			}
			if (condition.getTongbi()>0) {
				c = (c == null ? Cnd.where("tongbi", "=", condition.getTongbi()) : c
						.and("tongbi", "=", condition.getTongbi()));
			}
		}
		return c;
	}
}
