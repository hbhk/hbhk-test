package org.hbhk.test.solr.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.CommonsHttpSolrServer;
import org.apache.solr.client.solrj.impl.LBHttpSolrServer;
import org.apache.solr.client.solrj.response.FacetField.Count;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.junit.Test;

/**
 * @date 2013年12月4日
 * @author huangjie
 */
public class ItemForSolrTest {
	//指定solr服务器的地址
	private final static String URL = "http://localhost:1234/solr";
	
	@Test
	public void test1(){
	}
	
	/**
	 * 基于列表的添加
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void test2() throws SolrServerException, IOException{

	}
	
	/**
	 * 基于javabean的添加
	 * @throws SolrServerException
	 * @throws IOException
	 */
	@Test
	public void test3() throws SolrServerException, IOException{
		List<ItemForSolrCommand> msgs = new ArrayList<ItemForSolrCommand>();
		
		List<String> langs = new ArrayList<String>();
		langs.add("zh_cn");
		langs.add("en_us");
		langs.add("jp");
		for (int i = 0; i < 10; i++) {
			ItemForSolrCommand msg = new ItemForSolrCommand();
			msg.setId(Long.parseLong(i+""));
			msg.setTitle("title");
			Map<String, List<String>> map  = new HashMap<String, List<String>>();
			for (int j = 0; j < langs.size(); j++) {
				String lang = langs.get(j);
				msg.setDynamicTitle(map);
				List<String> val = new ArrayList<String>();
				val.add("dynamic_title_"+lang+i);
				map.put("dynamic_title_"+lang, val);
			}
			msg.setDynamicTitle(map);
			msgs.add(msg);
		}
		try {
			SolrServer server = new LBHttpSolrServer(URL);
			server.addBeans(msgs);
			server.commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void test4() throws SolrServerException, MalformedURLException{
		SolrServer server = new LBHttpSolrServer(URL);
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
	
	public static void add_FQAccurateForStringList(SolrQuery solrQuery,
			List<String> words, String type) {
		if (null != words && words.size() > 0) {
			String fq_keyword = "";
			int size = words.size();
			for (int i = 0; i < size; i++) {
				fq_keyword += type + ":" + words.get(i);
				if (i < size - 1) {
					fq_keyword += " OR ";
				}
			}
			solrQuery.addFilterQuery(fq_keyword);
		}
	}
	public static String escape(String s) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < s.length(); i++) {
			char c = s.charAt(i);
			// These characters are part of the query syntax and must be escaped
			if (c == '\\' || c == '+' || c == '-' || c == '!' || c == '('
					|| c == ')' || c == ':' || c == '^' || c == '[' || c == ']'
					|| c == '\"' || c == '{' || c == '}' || c == '~'
					|| c == '*' || c == '?' || c == '|' || c == '&') {
				sb.append('\\');
			}
			sb.append(c);
		}
		return sb.toString();
	}
	@Test
	public void test5() throws MalformedURLException, SolrServerException{
		try {
			SolrServer server = new LBHttpSolrServer(URL);
			//相当于QueryParser
			//SolrQuery query = new SolrQuery("keyword:dynamic_title_en_us1 OR keyword:dynamic_title_en_us2");
			SolrQuery query = new SolrQuery("keyword:dynamic_title_en_us1 OR keyword:dynamic_title_en_us2");
			List<String> words = new ArrayList<String>();
			words.add("dynamic_title_en_us1");
			words.add("dynamic_title_en_us2");
			add_FQAccurateForStringList(query, words, "keyword");
			query.setStart(0);
			query.setRows(50);
			QueryResponse res = server.query(query);
			//可以直接查询相应的bean对象，但是不是很常用
			//使用这种方式无法获取总数量
			List<ItemForSolrCommand> list = res.getBeans(ItemForSolrCommand.class);
			System.out.println("当前总数："+list.size());
			for(ItemForSolrCommand msg : list){
				Map<String, List<String>>  plist = msg.getDynamicTitle();
				StringBuilder sb = new StringBuilder();
				Set<String> keys = plist.keySet();
				if(plist!=null){
					for (String key : keys) {
						sb.append(plist.get(key).toString());
						if(key.equals("dynamic_title_en_us")){
							msg.setTitle(plist.get(key).toString());
						}
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
