package com.etree.rts.model.products;

import org.springframework.stereotype.Repository;


public class PriceModel {
	private String productId;
	private float value;
	private String pricelist;
	private String organizationalUnit;
	private String validFrom;
	private String articleCode;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public float getValue() {
		return value;
	}
	public void setValue(float value) {
		this.value = value;
	}
	public String getPricelist() {
		return pricelist;
	}
	public void setPricelist(String pricelist) {
		this.pricelist = pricelist;
	}
	public String getOrganizationalUnit() {
		return organizationalUnit;
	}
	public void setOrganizationalUnit(String organizationalUnit) {
		this.organizationalUnit = organizationalUnit;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	public String getArticleCode() {
		return articleCode;
	}
	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}
	
	
}
