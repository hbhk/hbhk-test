package org.hbhk.test.solr.test;

import org.apache.solr.client.solrj.SolrQuery;
import org.hbhk.test.solr.ISolrservice;
import org.hbhk.test.solr.ItemForSolrCommand;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:spring.xml" })
public class SolrTest {

	@Autowired
	ISolrservice<ItemForSolrCommand> solrservice;

	@Test
	public void addIndex() {
		try {
			ItemForSolrCommand msg = new ItemForSolrCommand();
			msg.setId("5");
			msg.setTitle("asdasd");
			solrservice.addIndex(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void query() {
		try {
			SolrQuery query = new SolrQuery("*:*");
			//实现分页的查询
			query.setStart(0);
			query.setRows(3);
			solrservice.query(query);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	
	@Test
	public void delAllIndexs() {
		try {
			solrservice.delAllIndexs();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
