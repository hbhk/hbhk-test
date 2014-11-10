package org.hbhk.test.solr.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

/**
 * @date 2013年12月4日
 * @author huangjie
 */
@SuppressWarnings("deprecation")
public class SolrTest {
	//指定solr服务器的地址
	private final static String URL = "http://localhost:1234/solr";
	
	@Test
	public void test1(){
		//1、创建SolrServer对象，该对象有两个可以使用，都是线程安全的
//		CommonsHttpSolrServer：启动web服务器使用的，通过http请求的
//		EmbeddedSolrServer：内嵌式的，导入solr的jar包就可以使用了
		try {
			SolrServer server = new CommonsHttpSolrServer(URL);
			//把查询出来的数据全部删除
//			server.deleteByQuery("*:*");
//			server.commit();
			
			SolrInputDocument doc = new SolrInputDocument();
			//id是必填的，并且是String类型的
			//<field name="id" type="string" indexed="true" stored="true" required="true" />
			//id是唯一的主键，当多次添加的时候，最后添加的相同id会覆盖前面的域
			doc.addField("id", "1");
			doc.addField("msg_title", "这是我的第一个solrj程序");
			doc.addField("msg_content", "solr程序的运行");
			server.add(doc);
			server.commit();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (SolrServerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 基于列表的添加
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void test2() throws SolrServerException, IOException{
		List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();
		SolrInputDocument doc = new SolrInputDocument();
		doc.addField("id", "2");
		doc.addField("msg_title", "很好，solr可以工作了");
		doc.addField("msg_content", "solr总算可以正式工作了");
		
		docs.add(doc);
		
		doc = new SolrInputDocument();
		doc.addField("id", "3");
		doc.addField("msg_title", "测试以下solr的添加");
		doc.addField("msg_content", "看看能不能添加一个列表信息");
		
		docs.add(doc);
		
		SolrServer server = new CommonsHttpSolrServer(URL);
		server.add(docs);
		server.commit();
	}
	
	/**
	 * 基于javabean的添加
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void test3() throws SolrServerException, IOException{
		List<Message> msgs = new ArrayList<Message>();
		//多值域的添加使用数组
		msgs.add(new Message("4","基于javabean的添加", new String[]{"javabean的添加附件","javabean的添加附件1"}));
		msgs.add(new Message("5","基于javabean的列表数据的添加", new String[]{"通过对象完成添加的附件","通过对象完成添加的附件1"}));
		
		SolrServer server = new CommonsHttpSolrServer(URL);
		server.addBeans(msgs);
		server.commit();
	}
	
	@Test
	public void test4() throws SolrServerException, MalformedURLException{
		SolrServer server = new CommonsHttpSolrServer(URL);
		//定义查询字符串
		SolrQuery query = new SolrQuery("*:*");
		//实现分页的查询
		query.setStart(0);
		query.setRows(3);
		QueryResponse res = server.query(query);
		//查询出来的结果都保存在SolrDocumentList中
		SolrDocumentList sdl = res.getResults();
		System.out.println("总数："+sdl.getNumFound());
		for(SolrDocument sd : sdl){
			System.out.println(sd.get("id")+"#"+sd.get("msg_title")+"#"+sd.get("msg_content"));
		}
	}
	
	@Test
	public void test5() throws MalformedURLException, SolrServerException{
		try {
			SolrServer server = new CommonsHttpSolrServer(URL);
			//相当于QueryParser
			SolrQuery query = new SolrQuery("allDisplay:true OR visiblePersons:16");
			//query.addFilterQuery("visiblePersons:16");
			query.setStart(1);
			query.setRows(50);
			QueryResponse res = server.query(query);
			//可以直接查询相应的bean对象，但是不是很常用
			//使用这种方式无法获取总数量
			List<ItemForSolrCommand> list = res.getBeans(ItemForSolrCommand.class);
			System.out.println("当前总数："+list.size());
			for(ItemForSolrCommand msg : list){
				List<String>  plist = msg.getVisiblePersons();
				StringBuilder sb = new StringBuilder();
				if(plist!=null){
					for (String string : plist) {
						sb.append(string);
					}
				}
				System.out.println(msg.getId()+"#"+msg.getTitle()+"#"+sb.toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test6() throws SolrServerException, MalformedURLException{
		SolrServer server = new CommonsHttpSolrServer(URL);
		SolrQuery query = new SolrQuery("msg_content:solr");
		query.setHighlight(true).setHighlightSimplePre("<span class='red'>").setHighlightSimplePost("</span>")
		.setStart(0).setRows(10);
		//hl.fl表示高亮的field，也就是高亮的区域
		query.setParam("hl.fl", "msg_title,msg_content");
		QueryResponse res = server.query(query);
		
		SolrDocumentList sdl = res.getResults();
		System.out.println("总数："+sdl.getNumFound());
		for(SolrDocument sd : sdl){
//			System.out.println(sd.get("id")+"#"+sd.get("msg_title")+"#"+sd.get("msg_content"));
			String id = (String) sd.get("id");
			//在solr这里对需要加高亮的字段必须要在索引中的store=true才行
			System.out.println(id+"#"+res.getHighlighting().get(id).get("msg_content"));;
			
		}
	}
	
	/**
	 * 测试分组
	 * @throws MalformedURLException 
	 * @throws SolrServerException 
	 */
	@Test
	public void test7() throws MalformedURLException, SolrServerException{
		SolrServer server = new CommonsHttpSolrServer(URL);
		SolrQuery query = new SolrQuery("city:*");
		query.setIncludeScore(false);
		query.setFacet(true);
		query.addFacetField("city");
		query.setFacetSort(true);
		QueryResponse res = server.query(query);
		List<Count> countList = res.getFacetField("city").getValues();
		
		for(Count count : countList){
			System.out.println(count.getName()+"#"+count.getCount());
		}
		/**
		 * 输出结果：
		 *  上海#5290
			深圳#2763
			广州#2504
			北京#1962
			东莞#1764
			杭州#1713
			苏州#1661
			南京#1529
		 */
	}

	
	
}
