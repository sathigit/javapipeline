package com.etree.rts.domain.products;

import java.io.Serializable;

public class ArticleCode implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 981780288285178331L;
	private String uuid;
	private String organizationId;
	private String code;
	private long quantity;
	
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public long getQuantity() {
		return quantity;
	}
	public void setQuantity(long quantity) {
		this.quantity = quantity;
	}
	public String getOrganizationId() {
		return organizationId;
	}
	public void setOrganizationId(String organizationId) {
		this.organizationId = organizationId;
	}

}
