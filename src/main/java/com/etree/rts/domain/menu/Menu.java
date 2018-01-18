package com.etree.rts.domain.menu;

import java.io.Serializable;
import java.util.Date;

import java.sql.Blob;

public class Menu implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6384849459739375095L;
	private String name;
	private Blob menu;
	private boolean isActive;
	private Date modifiedDate;
	private Date createdDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
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
