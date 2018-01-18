package com.etree.rts.service.menu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.dao.menu.MenuDAO;
import com.etree.rts.dao.packages.PackageDAO;
import com.etree.rts.dao.role.RoleDAO;
import com.etree.rts.domain.packages.Packages;
import com.etree.rts.domain.role.Role;
import com.etree.rts.model.menu.MenuModel;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.etree.rts.utils.MenuUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("menuService")
public class MenuServiceImpl implements MenuService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(MenuServiceImpl.class);

	@Autowired
	MenuDAO menuDAO;

	@Autowired
	RoleDAO roleDAO;

	@Autowired
	PackageDAO packageDAO;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public MenuTree getMenus() throws Exception {
		return mapper.readValue(menuDAO.getCoreMenu(), MenuTree.class);
	}

	@Override
	public MenuTree getMenuByRole(String packageId, String roleId, String userId) {
		try {
			if (userId != null && userId.equalsIgnoreCase("2")) {
				MenuTree menuTree = getMenus();
				Role role = roleDAO.getRole(roleId);
				MenuUtility.mergeTree(menuTree, role);
				return menuTree;
			} else {
				Packages packageTree = packageDAO.getPackage(packageId);
				MenuTree menuTree = mapper.readValue(CommonUtils.getBlobData(packageTree.getMenu()), MenuTree.class);
				Role role = roleDAO.getRole(roleId);
				MenuUtility.mergeTree(menuTree, role);
				return menuTree;
			}
		} catch (Exception e) {
			logger.error("Exceptio in getMenuByRole", e);
		}
		return null;
	}

	@Override
	public Response saveMenus(MenuModel menuModel) throws Exception {
		menuDAO.deleteMenus();
		return menuDAO.saveMenus(menuModel);
	}

}
