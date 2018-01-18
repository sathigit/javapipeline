package com.etree.rts.service.packagemenu;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.dao.packageMenu.PackageMenuDAO;
import com.etree.rts.dao.packages.PackageDAO;
import com.etree.rts.domain.packages.Packages;
import com.etree.rts.model.packagemenu.PackageMenuModel;
import com.etree.rts.model.packagemenu.PackageMenuTree;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("packageMenuService")
public class PackageMenuServiceImpl implements PackageMenuService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(PackageMenuServiceImpl.class);

	@Autowired
	PackageMenuDAO packageMenuDao;
	
	@Autowired
	PackageDAO packageDAO;	
	ObjectMapper mapper = new ObjectMapper();
	
	
	@Override
	public PackageMenuTree getMenus() throws Exception {
		return mapper.readValue(packageMenuDao.getCoreMenu(), PackageMenuTree.class);
	}
	
	@Override
	public PackageMenuTree getPackageMenuByPackageId(String packageId) {
		try {
			PackageMenuTree packageMenuTree = getMenus();
			mergeTree(packageMenuTree, packageId);
			return packageMenuTree;
		} catch (Exception e) {
			logger.error("Exceptio in getMenuByPackageId", e);
		}
		return null;
	}
	private void mergeTree(PackageMenuTree packageMenuTree, String packageId) throws Exception {
		// knowSelected takes Packages Tree Structure
		Packages packageTree = packageDAO.getPackage(packageId);
		PackageMenuTree menuTree = mapper.readValue(CommonUtils.getBlobData(packageTree.getMenu()), PackageMenuTree.class);
		packageTreeRecursive(knowSelected(menuTree), packageMenuTree.getSubMenus());
	}

	private void packageTreeRecursive(List<String> selectedList, List<PackageMenuTree> treeDatas) {
		for (PackageMenuTree data : treeDatas) {
			data.setSelected(selectedList.contains(data.getName()));
			packageTreeRecursive(selectedList, data.getSubMenus());
		}
	}

	public List<String> knowSelected(PackageMenuTree packageMenuTree) throws JsonProcessingException {
		List<String> selectedList = new ArrayList<String>();
		mapTree(selectedList, packageMenuTree);
		return selectedList;
	}

	private void mapTree(List<String> selectedList, PackageMenuTree packageMenuTree) {
		selectedList.add(packageMenuTree.getName());
		treeRecursive(selectedList, packageMenuTree.getSubMenus());
	}

	private void treeRecursive(List<String> selectedList, List<PackageMenuTree> packageMenuTrees) {
		for (PackageMenuTree packageMenuTree : packageMenuTrees) {
			if (packageMenuTree.getSelected())
				selectedList.add(packageMenuTree.getName());
			treeRecursive(selectedList, packageMenuTree.getSubMenus());
		}
	}

	
	@Override
	public Response saveMenus(PackageMenuModel packageMenuModel)
			throws Exception {
		packageMenuDao.deleteMenus();
		return packageMenuDao.saveMenus(packageMenuModel);
	}

	

	@Override
	public Response updatePackageMenu(PackageMenuModel packageMenuModel,String packageId) throws Exception {
		try {
			return packageMenuDao.updatePackageMenu(packageMenuModel,packageId);
		} catch (Exception ex) {
			logger.info("Exception in updatePackageMenu:" + ex.getMessage());
		}
		return null;
	}

	
	
}



