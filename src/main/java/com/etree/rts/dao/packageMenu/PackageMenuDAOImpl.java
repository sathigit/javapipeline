package com.etree.rts.dao.packageMenu;

import java.sql.Blob;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.packageMenu.PackageMenu;
import com.etree.rts.domain.packages.Packages;
import com.etree.rts.model.packagemenu.PackageMenuModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository

public class PackageMenuDAOImpl implements PackageMenuDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(PackageMenuDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RTSProperties rtsProperties;

	@Override
	public String getCoreMenu() {
		try {
			String sql = "SELECT * FROM packageMenu";
			PackageMenu menu = (PackageMenu) jdbcTemplate.queryForObject(sql,new Object[] {}, new BeanPropertyRowMapper(PackageMenu.class));
			return CommonUtils.getBlobData(menu.getMenu());
		} catch (Exception e) {
			logger.error("Exception in getCoreMenu :", e);
		}
		return null;
	}

	@Override
	public void deleteMenus() {
		try {
			String sql = "delete FROM packageMenu";
			int rows = jdbcTemplate.update(sql);
			
		} catch (Exception e) {
			logger.error("deleteExpiredCampaigns:Error: ", e);
		}
			
	}

	@Override
	public Response saveMenus(PackageMenuModel packageMenuModel)
			throws Exception {
		Response response = CommonUtils.getResponseObject("Save Menus");
		try {
			PackageMenu menu = new PackageMenu();
			BeanUtils.copyProperties(packageMenuModel, menu);
			menu.setIsActive(true);
			
			byte[] bytes = CommonUtils.getJson(packageMenuModel.getMenuTree()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			menu.setMenu(blob);
			String sql = "INSERT INTO packageMenu(name,menu,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { menu.getName(), menu.getMenu(), menu.getIsActive(), new Date(), new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in saveRole", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	@Override
	public Response updatePackageMenu(PackageMenuModel packageMenuModel,String PackageId)
			throws Exception {
		Response response = CommonUtils.getResponseObject("Add Updated Package Menu");
		try {

			Packages packagesMenu = new Packages();
			BeanUtils.copyProperties(packageMenuModel, packagesMenu);
			byte[] bytes = CommonUtils.getJson(packageMenuModel.getMenuTree()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			packagesMenu.setMenu(blob);
			packagesMenu.setPackageId(PackageId);
			String sql = "UPDATE packageMenu SET name=?,alias=?,menu=?,isActive=?, modifiedDate=? WHERE packageId=?";
			int res = jdbcTemplate.update(sql, packagesMenu.getName(), packagesMenu.getAlias(), packagesMenu.getMenu(), packagesMenu.getIsActive(),
					new Date(), packagesMenu.getPackageId());
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in updateRole", e);
			return CommonUtils.getDuplicateKeyMessage(response, e);
		}
		return response;
	}

	

}
