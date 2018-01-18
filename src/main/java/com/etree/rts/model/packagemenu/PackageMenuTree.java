package com.etree.rts.model.packagemenu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;



public class PackageMenuTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2768829150626562342L;
	String name;
	String link;
	String menuId;
	String icon;

	Boolean selected = false;

	List<PackageMenuTree> subMenus = new ArrayList<>();

	public PackageMenuTree() {
	}

	public PackageMenuTree(String name) {
		this.name = name;
	}
	public PackageMenuTree(String name, Boolean selected) {
		this.name = name;
		this.selected = selected;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public Boolean getSelected() {
		return selected;
	}

	public void setSelected(Boolean selected) {
		this.selected = selected;
	}

	public List<PackageMenuTree> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<PackageMenuTree> subMenus) {
		this.subMenus = subMenus;
	}

	

}
