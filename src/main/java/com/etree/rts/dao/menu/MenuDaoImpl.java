package com.etree.rts.dao.menu;

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
import com.etree.rts.domain.menu.Menu;
import com.etree.rts.model.menu.MenuModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;

@Repository
public class MenuDaoImpl implements MenuDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(MenuDaoImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	@Autowired
	RTSProperties rtsProperties;

	// @Override
	// public String getCoreMenu() {
	// try {
	// return new
	// String(Files.readAllBytes(Paths.get(rtsProperties.getCoreMenuPath())));
	// } catch (IOException e) {
	// logger.error("Exception in getCoreMenu :", e);
	// }
	// return null;
	// }
	@Override
	public String getCoreMenu() {
		try {
			String sql = "SELECT * FROM menu";
			Menu menu = (Menu) jdbcTemplate.queryForObject(sql, new Object[] {}, new BeanPropertyRowMapper(Menu.class));
			return CommonUtils.getBlobData(menu.getMenu());
		} catch (Exception e) {
			logger.error("Exception in getCoreMenu :", e);
		}
		return null;
	}


	@Override
	public Response saveMenus(MenuModel menuModel) throws Exception {
		Response response = CommonUtils.getResponseObject("Save Menus");
		try {
			Menu menu = new Menu();
			BeanUtils.copyProperties(menuModel, menu);
			menu.setIsActive(true);
			byte[] bytes = CommonUtils.getJson(menuModel.getMenuTree()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			menu.setMenu(blob);
			String sql = "INSERT INTO menu(name,menu,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?)";
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
	public int deleteMenus() {
		try {
			String sql = "delete FROM menu";
			int rows = jdbcTemplate.update(sql);
			return rows;
		} catch (Exception e) {
			logger.error("deleteExpiredCampaigns:Error: ", e);
		}
		return 0;
	}
}