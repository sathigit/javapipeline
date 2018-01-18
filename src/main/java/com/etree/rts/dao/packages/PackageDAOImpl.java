package com.etree.rts.dao.packages;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.packages.Packages;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.model.packages.PackagesModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class PackageDAOImpl implements PackageDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(PackageDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Response savePackage(PackagesModel packagesModel) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Package");
		try {

			Packages packages = new Packages();
			BeanUtils.copyProperties(packagesModel, packages);
			packages.setIsActive(true);
			packages.setPackageId(CommonUtils.getRandomUUID());
			
			getAbstractMenu(packagesModel);
			
			byte[] bytes = CommonUtils.getJson(packagesModel.getMenu()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			packages.setMenu(blob);
			String sql = "INSERT INTO package(packageId,name,alias,menu,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { packages.getPackageId(), packages.getName(),
							packages.getAlias(), packages.getMenu(), packages.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in savePackage", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	private void getAbstractMenu(PackagesModel packagesModel) {
		Stack<MenuTree> menuStack = new Stack<MenuTree>();
		List<MenuTree> list = new ArrayList<MenuTree>();
		menuStack.push(packagesModel.getMenu());
		getSelectedMenuItems(menuStack, list);

		for (MenuTree menu : list) {
			removeUnselectedMenu(menu);
		}
	}

	@Override
	public List<Packages> getPackages() throws Exception {
		try {
			String sql = "SELECT * FROM package where packageId<>'rts-pacakge'";
			List<Packages> packages = jdbcTemplate.query(sql, new Object[] {},
					new BeanPropertyRowMapper<Packages>(Packages.class));
			return packages;
		} catch (Exception e) {
			logger.error("Exception in getPackages", e);
		}
		return null;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Packages getPackage(String packageId) throws Exception {
		try {
			String sql = "SELECT * FROM package where packageId=?";
			return (Packages) jdbcTemplate.queryForObject(sql, new Object[] { packageId },
					new BeanPropertyRowMapper(Packages.class));
		} catch (Exception e) {
			logger.error("Exception in getPackage", e);
			return null;
		}
	}

	@Override
	public Response updatePackage(PackagesModel packagesModel) {
		Response response = CommonUtils.getResponseObject("Update Package");
		try {

			Packages packages = new Packages();
			BeanUtils.copyProperties(packagesModel, packages);
			getAbstractMenu(packagesModel);
			byte[] bytes = CommonUtils.getJson(packagesModel.getMenu()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			packages.setMenu(blob);
			String sql = "UPDATE package SET name=?,alias=?,menu=?,isActive=?, modifiedDate=? WHERE packageId=?";
			int res = jdbcTemplate.update(sql, packages.getName(), packages.getAlias(),
					packages.getMenu(), packages.getIsActive(), new Date(), packages.getPackageId());
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				response.setData(packagesModel.getMenu());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updatePackages", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response deletePackage(String packageId) {
		Response response = CommonUtils.getResponseObject("Delete package data");
		try {
			String sql = "DELETE FROM package  WHERE packageId=?";

			int rows = jdbcTemplate.update(sql, packageId);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete package data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}
	
	/**
	 * Get the Selected Menu Items recursively
	 * 
	 * @param menuStack
	 * @param list
	 */
	private static void getSelectedMenuItems(Stack<MenuTree> menuStack, List<MenuTree> list) {
		MenuTree mn = menuStack.pop();
		if (mn.getSubMenus().size() == 0) {
			if (mn.getSelected()) {
				setParents(mn);
			} else {
				list.add(mn);
			}
			return;
		}
		if (mn.getSubMenus().size() > 0) {
			for (MenuTree m : mn.getSubMenus()) {
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
	private static void removeUnselectedMenu(MenuTree menu) {
		if (!menu.getSelected() && null != menu.getParent() && null != menu.getParent().getSubMenus()) {
			menu.getParent().getSubMenus().remove(menu);
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
	private static MenuTree setParents(MenuTree menu) {
		menu.setSelected(true);
		if (menu.getParent() == null) {
			return menu;
		} else {
			return setParents(menu.getParent());
		}
	}
}
