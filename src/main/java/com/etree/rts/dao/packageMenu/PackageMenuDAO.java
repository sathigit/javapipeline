package com.etree.rts.dao.packageMenu;

import com.etree.rts.model.packagemenu.PackageMenuModel;
import com.etree.rts.response.Response;

public interface PackageMenuDAO {

	String getCoreMenu();

	void deleteMenus();

	Response saveMenus(PackageMenuModel packageMenuModel) throws Exception;



	Response updatePackageMenu(PackageMenuModel packageMenuModel,
			String packageId)throws Exception;

	

}
