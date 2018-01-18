package com.etree.rts.domain.packages;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

public class Packages implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 444533593437317082L;
	private String packageId;
	private String name;
	private String alias;
	private Blob menu;
	private boolean isActive;
	private Date modifiedDate;
	private Date createdDate;

	public String getPackageId() {
		return packageId;
	}

	public void setPackageId(String packageId) {
		this.packageId = packageId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public Blob getMenu() {
		return menu;
	}

	public void setMenu(Blob menu) {
		this.menu = menu;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public Date getModifiedDate() {
		return modifiedDate;
	}

	public void setModifiedDate(Date modifiedDate) {
		this.modifiedDate = modifiedDate;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

}
