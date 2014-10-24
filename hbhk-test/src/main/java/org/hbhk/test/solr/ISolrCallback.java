package org.hbhk.test.solr;

import org.apache.solr.client.solrj.SolrServer;

public interface ISolrCallback<T> {

	T doExecute(SolrServer solrServer);

}
