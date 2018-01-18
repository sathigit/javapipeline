package com.etree.rts.model.packagemenu;

import java.io.Serializable;
import java.util.Date;



public class PackageMenuModel implements Serializable { /**
	 * 
	 */
	private static final long serialVersionUID = 802554908179710960L;
	private String name;
	private PackageMenuTree menuTree;
	private boolean isActive;
	private Date modifiedDate;
	private Date createdDate;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public PackageMenuTree getMenuTree() {
		return menuTree;
	}
	public void setMenuTree(PackageMenuTree menuTree) {
		this.menuTree = menuTree;
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
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
