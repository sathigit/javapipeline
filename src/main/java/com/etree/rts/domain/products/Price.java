package com.etree.rts.domain.products;

import java.io.Serializable;
import java.util.Date;

public class Price implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3086029535453641635L;
	private String uuid;
	private double value;
	private String pricelist;
	private String organizationalUnit;
	private Date validFrom;
	private String articleCode;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public double getValue() {
		return value;
	}

	public void setValue(double value) {
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

	public Date getValidFrom() {
		return validFrom;
	}

	public void setValidFrom(Date validFrom) {
		this.validFrom = validFrom;
	}

	public String getArticleCode() {
		return articleCode;
	}

	public void setArticleCode(String articleCode) {
		this.articleCode = articleCode;
	}

}
