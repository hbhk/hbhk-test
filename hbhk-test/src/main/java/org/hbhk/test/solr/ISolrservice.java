package org.hbhk.test.solr;

import java.util.List;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.params.SolrParams;
import org.hbhk.test.solr.impl.SolrBase;

public interface ISolrservice<T extends SolrBase> {

	void updateIndex(List<T> list);

	void addIndex(T t);

	void addIndexs(List<T> list);

	void delAllIndexs();

	void delIndexs(List<T> list);

	List<T> queryByField(String queryString, String sortString,
			Integer startNum, Integer rowsNum, Class<T> cls);

	QueryResponse query(SolrParams params);
}
