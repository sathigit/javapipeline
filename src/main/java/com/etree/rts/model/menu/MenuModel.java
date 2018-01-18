package com.etree.rts.model.menu;

import java.io.Serializable;
import java.util.Date;

public class MenuModel implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2508104079303891577L;
	private String name;
	private MenuTree menuTree;
	private boolean isActive;
	private Date modifiedDate;
	private Date createdDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public MenuTree getMenuTree() {
		return menuTree;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public void setMenuTree(MenuTree menuTree) {
		this.menuTree = menuTree;
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
