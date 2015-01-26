package org.hbhk.test.solr.test;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.solr.client.solrj.beans.Field;

public class ItemForSolrCommand  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 唯一值。其他id用于业务属性，仅仅用于作为标识solr索引的id。 **/
	@Field
	private Long id;
	
	/**
	 * 关键字
	 */
	@Field
	private String keyword;
	
	/**
	 * 商品名称
	 */
	@Field
	private String title;

	
	/**
	 * 动态属性值
	 */
	@Field("dynamic_title_*")
	private Map<String, List<String>> dynamicTitle;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Map<String, List<String>> getDynamicTitle() {
		return dynamicTitle;
	}

	public void setDynamicTitle(Map<String, List<String>> dynamicTitle) {
		this.dynamicTitle = dynamicTitle;
	}

}
