package com.etree.rts.service.packages;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.packages.PackageDAO;
import com.etree.rts.dao.role.RoleDAO;
import com.etree.rts.domain.packages.Packages;
import com.etree.rts.mapper.packages.PackagesMapper;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.model.packages.PackagesModel;
import com.etree.rts.response.Response;
import com.etree.rts.service.menu.MenuService;
import com.etree.rts.utils.CommonUtils;
import com.etree.rts.utils.MenuUtility;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("packageService")
public class PackageServiceImpl implements PackageService {

	@Autowired
	PackageDAO packageDao;

	@Autowired
	PackagesMapper packagesMapper;
	@Autowired
	MenuService menuService;

	@Autowired
	RoleDAO roleDAO;

	ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(PackageServiceImpl.class);

	@Override
	public Response savePackage(PackagesModel packagesModel) {
		try {
			return packageDao.savePackage(packagesModel);
		} catch (Exception ex) {
			logger.info("Exception in savePackages:" + ex.getMessage());
		}
		return null;

	}

	@Override
	public List<PackagesModel> getPackages() {
		try {
			List<Packages> packages = packageDao.getPackages();
			List<PackagesModel> packagesModels = new ArrayList<PackagesModel>();
			for (Packages packageX : packages) {
				PackagesModel packagesModel = new PackagesModel();
				BeanUtils.copyProperties(packageX, packagesModel);
				MenuTree menuTree = mapper.readValue(CommonUtils.getBlobData(packageX.getMenu()), MenuTree.class);
				packagesModel.setMenu(menuTree);
				packagesModels.add(packagesModel);
			}

			return packagesModels;
		} catch (Exception ex) {
			logger.info("Exception getPackages:", ex);
		}
		return null;
	}

	@Override
	public PackagesModel getPackage(String packageId, String userId) {
		try {
			Packages packages = packageDao.getPackage(packageId);
			PackagesModel packagesModel = new PackagesModel();
			if (packages == null)
				return null;
			BeanUtils.copyProperties(packages, packagesModel);
			MenuTree packageMenu = mapper.readValue(CommonUtils.getBlobData(packages.getMenu()), MenuTree.class);
			if (userId != null && userId.equalsIgnoreCase("2")) {
				MenuTree mainMenu = menuService.getMenus();
				MenuUtility.mergePacakgeTree(mainMenu, packageMenu);
				packagesModel.setMenu(mainMenu);
			}else{
				packagesModel.setMenu(packageMenu);
			}
			return packagesModel;
		} catch (Exception e) {
			logger.info("Exception in getPackage:", e);
			return null;
		}
	}

	@Override
	public Response updatePackage(PackagesModel packagesModel) {
		try {
			Response response = packageDao.updatePackage(packagesModel);
			if (response.getStatus().equals(StatusCode.SUCCESS.name())) {
				MenuTree menuTree = (MenuTree) response.getData();
				roleDAO.updateMenusByPackage(menuTree, packagesModel.getPackageId());
				response.setData(null);
			}
			return response;
		} catch (Exception ex) {
			logger.info("Exception in updatePackage:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public Response deletePackage(String packageId) {
		try {
			return packageDao.deletePackage(packageId);
		} catch (Exception ex) {
			logger.info("Exception in deletePackage:" + ex.getMessage());
		}
		return null;

	}

}
