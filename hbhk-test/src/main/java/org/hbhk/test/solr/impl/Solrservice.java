package org.hbhk.test.solr.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.apache.solr.common.params.SolrParams;
import org.hbhk.test.solr.ISolrCallback;
import org.hbhk.test.solr.ISolrservice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Solrservice implements ISolrservice<SolrBase> {

	@Autowired
	private SolrServer solrServer;

	public void rollback() {
		try {
			solrServer.rollback();
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void updateIndex(List<SolrBase> list) {
		try {
			List<String> ids = new ArrayList<String>();
			for (SolrBase solr : list) {
				ids.add(solr.getId());
			}
			solrServer.deleteById(ids);
			solrServer.addBeans(list);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	public void addIndexs(List<SolrBase> list) {
		try {
			solrServer.addBeans(list);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}

	}

	public void addIndex(SolrBase t) {
		try {
			solrServer.addBean(t);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}

	}

	public List<SolrBase> queryByField(String queryString, String sortString,
			Integer startNum, Integer rowsNum, Class<SolrBase> cls) {
		ModifiableSolrParams params = new ModifiableSolrParams();
		if (queryString.equals("")) {
			queryString = "*:*";
		}
		params.set("q", queryString);
		if (startNum < 0) {
			startNum = 0;
		}
		if (rowsNum < 1) {
			rowsNum = 1;
		}
		if (startNum > rowsNum) {
			int t = startNum;
			startNum = rowsNum;
			rowsNum = t;
		}
		params.set("start", startNum);
		params.set("rows", rowsNum);
		// 排序，，比如score desc，id desc(or asc)
		if (sortString.equals("")) {
			sortString = "score desc";
		}
		params.set("sort", sortString);
		// 返回信息 * 为全部 这里是全部加上score，如果不加下面就不能使用score
		params.set("fl", "*,score");
		List<SolrBase> list = new ArrayList<SolrBase>();
		try {
			QueryResponse response = solrServer.query(params);
			list = response.getBeans(cls);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}

		return list;
	}

	public QueryResponse query(SolrParams params) {
		try {
			return  solrServer.query(params);
		} catch (SolrServerException e) {
			throw new RuntimeException(e);
		}
	}

	public <T> T execute(ISolrCallback<T> action) {
		try {
			T t = action.doExecute(solrServer);
			solrServer.commit();
			return t;
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}

	}

	public void delAllIndexs() {
		try {
			solrServer.deleteByQuery("*:*");
			solrServer.commit();
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

	public void delIndexs(List<SolrBase> list) {
		try {
			List<String> ids = new ArrayList<String>();
			for (String id : ids) {
				ids.add(id);
			}
			solrServer.deleteById(ids);
		} catch (SolrServerException e) {
			rollback();
			throw new RuntimeException(e);
		} catch (IOException e) {
			rollback();
			throw new RuntimeException(e);
		}
	}

}
