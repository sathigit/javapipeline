package com.etree.rts.model.menu;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class MenuTree implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9062459186170036321L;
	String name;
	String link;
	String menuId;
	String icon;
	String desc;
	Boolean selected = false;

	List<MenuTree> subMenus = new ArrayList<>();
	MenuTree parent;
	public MenuTree() {
	}

	public MenuTree(String name) {
		this.name = name;
	}

	public MenuTree(String name, Boolean selected) {
		this.name = name;
		this.selected = selected;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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

	public List<MenuTree> getSubMenus() {
		return subMenus;
	}

	public void setSubMenus(List<MenuTree> subMenus) {
		this.subMenus = subMenus;
	}

	@JsonIgnore
	public MenuTree getParent() {
		return parent;
	}

	public void setParent(MenuTree parent) {
		this.parent = parent;
	}

}
