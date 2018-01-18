package com.etree.rts.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;
public class MenuItemRecursion {

	public static void main(String[] args) {

		// ds
		Menu m111 = new Menu("add", true, null);
		Menu m112 = new Menu("update", false, null);
		Menu m113 = new Menu("delete", true, null);

		Menu m121 = new Menu("add", false, null);
		Menu m122 = new Menu("update", true, null);

		Menu m1311 = new Menu("add", false, null);
		Menu m1312 = new Menu("update", false, null);

		Menu m11 = new Menu("user",true , Arrays.asList(new Menu[] { m111, m112, m113 }));
		Menu m12 = new Menu("role", true, Arrays.asList(new Menu[] { m121, m122 }));

		Menu m131 = new Menu("supplier", false, Arrays.asList(new Menu[] { m1311, m1312 }));
		Menu m13 = new Menu("sales", false, Arrays.asList(new Menu[] { m131 }));

		Menu menuRoot = new Menu("employee", true, Arrays.asList(new Menu[] { m11, m12, m13 }));

		Menu root = new Menu("mainRoot", true, Arrays.asList(new Menu[] { menuRoot }));

		Stack<Menu> menuStack = new Stack<Menu>();
		List<Menu> list = new ArrayList<Menu>();
		menuStack.push(root);
		getSelectedMenuItems(menuStack, list);

		for (Menu menu : list) {
			removeUnselectedMenu(menu);
		}
		System.out.println("Result : " + root.toString());
	}

	/**
	 * Get the Selected Menu Items recursively
	 * 
	 * @param menuStack
	 * @param list
	 */
	private static void getSelectedMenuItems(Stack<Menu> menuStack, List<Menu> list) {
		Menu mn = menuStack.pop();
		if (mn.getSubMenu().size() == 0) {
			if (mn.isSelected()) {
				setParents(mn);
			} else {
				list.add(mn);
			}
			return;
		}
		if (mn.getSubMenu().size() > 0) {
			for (Menu m : mn.getSubMenu()) {
				m.setParent(mn);
				menuStack.push(m);
				getSelectedMenuItems(menuStack, list);
			}
		}
	}

	/**
	 * Recursively remove un selected menu items
	 * 
	 * @param menu
	 */
	private static void removeUnselectedMenu(Menu menu) {
		if (!menu.isSelected() && null != menu.getParent() && null != menu.getParent().getSubMenu()) {
			menu.getParent().getSubMenu().remove(menu);
			System.out.println(menu.getName() + ">> Deleted ");
			removeUnselectedMenu(menu.getParent());
		}
	}

	/**
	 * Recursively set the parents as selected = true
	 * 
	 * @param menu
	 * @return
	 */
	private static Menu setParents(Menu menu) {
		menu.setSelected(true);
		System.out.print(menu.getName() + ":: ");
		if (menu.getParent() == null) {
			return menu;
		} else {
			return setParents(menu.getParent());
		}
	}

}

class Menu {
	String name;
	boolean isSelected;
	List<Menu> subMenu = new ArrayList<Menu>();
	Menu parent;

	Menu(String name, boolean isSelected, List<Menu> subMenu) {
		this.name = name;
		this.isSelected = isSelected;
		if (null != subMenu) {
			this.subMenu.addAll(subMenu);
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isSelected() {
		return isSelected;
	}

	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
	}

	public List<Menu> getSubMenu() {
		return subMenu;
	}

	public void setSubMenu(List<Menu> subMenu) {
		this.subMenu = subMenu;
	}

	public Menu getParent() {
		return parent;
	}

	public void setParent(Menu parent) {
		this.parent = parent;
	}

	@Override
	public String toString() {
		return "Menu [name=" + name + ", isSelected=" + isSelected + ", subMenu=" + subMenu + "]";
	}
	
	
}