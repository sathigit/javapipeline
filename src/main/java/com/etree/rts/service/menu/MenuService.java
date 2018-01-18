package com.etree.rts.service.menu;

import com.etree.rts.model.menu.MenuModel;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.response.Response;

public interface MenuService {

	MenuTree getMenus() throws Exception;

	MenuTree getMenuByRole(String packageId,String roleId,String userId);

	Response saveMenus(MenuModel menuModel) throws Exception;
}
