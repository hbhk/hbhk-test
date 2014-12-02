package org.hbhk.test.i18n;

import java.io.Serializable;

public class KeyValue implements Serializable {

	private static final long serialVersionUID = 8851795068347294831L;

	private String category;

	private String fieldName;
	private String i18nKey;

	private String value;

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getI18nKey() {
		return i18nKey;
	}

	public void setI18nKey(String i18nKey) {
		this.i18nKey = i18nKey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
