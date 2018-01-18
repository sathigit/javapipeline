package com.etree.rts.service.packagemenu;

import com.etree.rts.model.packagemenu.PackageMenuModel;
import com.etree.rts.model.packagemenu.PackageMenuTree;
import com.etree.rts.response.Response;

public interface PackageMenuService {

	Response saveMenus(PackageMenuModel packageMenuModel) throws Exception;

	PackageMenuTree getMenus() throws Exception;

	Response updatePackageMenu(PackageMenuModel packageMenuModel,
			String packageId) throws Exception;

	PackageMenuTree getPackageMenuByPackageId(String packageId);

}
