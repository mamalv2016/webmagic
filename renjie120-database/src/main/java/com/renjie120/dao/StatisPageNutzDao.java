package com.renjie120.dao;

import org.nutz.dao.Chain;
import org.nutz.dao.Cnd;

import com.alibaba.druid.util.StringUtils;
import com.renjie120.dto.StatisPage;

public class StatisPageNutzDao extends DaoTool<StatisPage> { 
	@Override
	Chain makeUpdateValue(StatisPage condition) {
		Chain ans = null;
		if (condition != null) {
			if (!StringUtils.isEmpty(condition.getDeleteFlag())) {
				ans = (ans == null ? Chain.make("deleteFlag",
						condition.getDeleteFlag()) : ans.add("deleteFlag",
						condition.getDeleteFlag()));
			}
			if (!StringUtils.isEmpty(condition.getStatus())) {
				ans = (ans == null ? Chain
						.make("status", condition.getStatus()) : ans.add(
						"status", condition.getStatus()));
			}
			if (!StringUtils.isEmpty(condition.getTitle())) {
				ans = (ans == null ? Chain.make("title", condition.getTitle())
						: ans.add("title", condition.getTitle()));
			}
			if (!StringUtils.isEmpty(condition.getUrl())) {
				ans = (ans == null ? Chain.make("url", condition.getUrl())
						: ans.add("url", condition.getUrl()));
			}
			if (!StringUtils.isEmpty(condition.getInfo())) {
				ans = (ans == null ? Chain.make("info", condition.getInfo())
						: ans.add("info", condition.getInfo()));
			}
		}
		return ans;
	}

	@Override
	Cnd makeUpdateWhere(StatisPage condition) {
		Cnd c = null;
		if (condition != null) {
			if (condition.getId() != null && condition.getId() > 0) {
				c = (c == null ? Cnd.where("id", "=", condition.getId()) : c
						.and("id", "=", condition.getId()));
			}
			if (!StringUtils.isEmpty(condition.getDeleteFlag())) {
				c = (c == null ? Cnd.where("deleteFlag", "=",
						condition.getDeleteFlag()) : c.and("deleteFlag", "=",
						condition.getDeleteFlag()));
			}
			if (!StringUtils.isEmpty(condition.getStatus())) {
				c = (c == null ? Cnd
						.where("status", "=", condition.getStatus()) : c.and(
						"status", "=", condition.getStatus()));
			}
			if (!StringUtils.isEmpty(condition.getTitle())) {
				c = (c == null ? Cnd.where("title", "LIKE",
						condition.getTitle()) : c.and("title", "LIKE",
						condition.getTitle()));
			}
			if (!StringUtils.isEmpty(condition.getUrl())) {
				c = (c == null ? Cnd.where("url", "LIKE", condition.getUrl())
						: c.and("url", "LIKE", condition.getUrl()));
			}
		}
		return c;
	}

	@Override
	Cnd makeQueryCondition(StatisPage condition) {
		Cnd c = null;
		if (condition != null) {
			if (condition.getId() != null && condition.getId() > 0) {
				c = (c == null ? Cnd.where("id", "=", condition.getId()) : c
						.and("id", "=", condition.getId()));
			}
			if (!StringUtils.isEmpty(condition.getDeleteFlag())) {
				c = (c == null ? Cnd.where("deleteFlag", "=",
						condition.getDeleteFlag()) : c.and("deleteFlag", "=",
						condition.getDeleteFlag()));
			}
			if (!StringUtils.isEmpty(condition.getStatus())) {
				c = (c == null ? Cnd
						.where("status", "=", condition.getStatus()) : c.and(
						"status", "=", condition.getStatus()));
			}
			if (!StringUtils.isEmpty(condition.getTitle())) {
				c = (c == null ? Cnd.where("title", "LIKE",
						condition.getTitle()) : c.and("title", "LIKE",
						condition.getTitle()));
			}
			if (!StringUtils.isEmpty(condition.getUrl())) {
				c = (c == null ? Cnd.where("url", "LIKE", condition.getUrl())
						: c.and("url", "LIKE", condition.getUrl()));
			}
		}
		return c;
	}
}
