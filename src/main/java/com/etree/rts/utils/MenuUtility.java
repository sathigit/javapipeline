package com.etree.rts.utils;

import java.util.ArrayList;
import java.util.List;

import com.etree.rts.domain.role.Role;
import com.etree.rts.model.menu.MenuTree;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class MenuUtility {

	static ObjectMapper mapper = new ObjectMapper();

	private MenuUtility() {

	}

	public static void mergeTree(MenuTree menuTree, Role role) throws Exception {
		MenuTree roleMenuTree = mapper.readValue(CommonUtils.getBlobData(role.getMenu()), MenuTree.class);
		roleTreeRecursive(knowSelected(roleMenuTree), menuTree.getSubMenus());
	}

	private static void roleTreeRecursive(List<String> selectedList, List<MenuTree> treeDatas) {
		for (MenuTree data : treeDatas) {
			data.setSelected(selectedList.contains(data.getName()));
			roleTreeRecursive(selectedList, data.getSubMenus());
		}
	}

	private static List<String> knowSelected(MenuTree menuTree) throws JsonProcessingException {
		List<String> selectedList = new ArrayList<String>();
		mapTree(selectedList, menuTree);
		return selectedList;
	}

	private static void mapTree(List<String> selectedList, MenuTree menuTree) {
		selectedList.add(menuTree.getName());
		treeRecursive(selectedList, menuTree.getSubMenus());
	}

	private static void treeRecursive(List<String> selectedList, List<MenuTree> menuTrees) {
		for (MenuTree menuTree : menuTrees) {
			if (menuTree.getSelected())
				selectedList.add(menuTree.getName());
			treeRecursive(selectedList, menuTree.getSubMenus());
		}
	}

	public static void mergePacakgeTree(MenuTree mainMenu, MenuTree packageMenu) throws Exception {
		roleTreeRecursive(knowSelected(packageMenu), mainMenu.getSubMenus());
	}
}
