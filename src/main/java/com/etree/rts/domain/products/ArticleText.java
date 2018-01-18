package com.etree.rts.domain.products;

import java.io.Serializable;

public class ArticleText implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5581426665257012277L;
	private String uuid;
	private String type;
	private String text;
	private String language;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
