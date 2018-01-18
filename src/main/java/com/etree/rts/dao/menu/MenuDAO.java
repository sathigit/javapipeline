package com.etree.rts.dao.menu;

import com.etree.rts.model.menu.MenuModel;
import com.etree.rts.response.Response;

public interface MenuDAO {

	String getCoreMenu();

	Response saveMenus(MenuModel menuModel) throws Exception;

	int deleteMenus();

}