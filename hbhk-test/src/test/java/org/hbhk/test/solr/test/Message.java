package org.hbhk.test.solr.test;

import org.apache.solr.client.solrj.beans.Field;

/**
 * 在变量的set方法上注解上lucene内部的字段名称
 */
public class Message {
	private String id;
	private String title;
	private String content[];
	
	
	public Message() {
		super();
	}

	public Message(String id, String title, String[] content) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
	}

	public String getId() {
		return id;
	}
	
	@Field
	public void setId(String id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	
	@Field("msg_title")
	public void setTitle(String title) {
		this.title = title;
	}
	public String[] getContent() {
		return content;
	}
	
	@Field("msg_content")
	public void setContent(String[] content) {
		this.content = content;
	}
}

