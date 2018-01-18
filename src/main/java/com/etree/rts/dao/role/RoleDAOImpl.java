package com.etree.rts.dao.role;

import java.sql.Blob;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.domain.role.Role;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.model.role.RoleModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

@Repository
public class RoleDAOImpl implements RoleDAO, Constants {
	private static final Logger logger = LoggerFactory.getLogger(RoleDAOImpl.class);

	@Autowired
	JdbcTemplate jdbcTemplate;

	ObjectMapper mapper = new ObjectMapper();

	@Override
	public Response saveRole(RoleModel roleModel) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Role");
		try {

			Role role = new Role();
			BeanUtils.copyProperties(roleModel, role);
			role.setIsActive(true);
			role.setRoleId(CommonUtils.getRandomUUID());
			byte[] bytes = CommonUtils.getJson(roleModel.getMenu()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			role.setMenu(blob);
			String sql = "INSERT INTO role(roleId,name,alias,menu,organizationId,packageId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { role.getRoleId(), role.getName(), role.getAlias(), role.getMenu(),
							role.getOrganizationId(), role.getPackageId(), role.getIsActive(), new Date(),
							new Date() });
			if (res == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
				RoleModel roleModel1 = new RoleModel();
				BeanUtils.copyProperties(role, roleModel1);
				MenuTree menuTree = mapper.readValue(CommonUtils.getBlobData(role.getMenu()), MenuTree.class);
				roleModel1.setMenu(menuTree);
				response.setData(roleModel1);
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
	public Response saveAdminRole(Role role) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Admin Role");
		try {

			String sql = "INSERT INTO role(roleId,name,alias,menu,organizationId,packageId,isActive,createdDate,modifiedDate) VALUES(?,?,?,?,?,?,?,?,?)";
			int res = jdbcTemplate.update(sql,
					new Object[] { role.getRoleId(), role.getName(), role.getAlias(), role.getMenu(),
							role.getOrganizationId(), role.getPackageId(), role.getIsActive(), new Date(),
							new Date() });
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
	public List<Role> getRoles() throws Exception {
		try {
			String sql = "SELECT * FROM role";
			List<Role> roles = jdbcTemplate.query(sql, new Object[] {}, new BeanPropertyRowMapper<Role>(Role.class));
			return roles;
		} catch (Exception e) {
			logger.error("Exception in getRoles", e);
		}
		return null;
	}

	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Role getRole(String roleId) throws Exception {
		try {
			String sql = "SELECT * FROM role where roleId=?";
			return (Role) jdbcTemplate.queryForObject(sql, new Object[] { roleId },
					new BeanPropertyRowMapper(Role.class));
		} catch (Exception e) {
			logger.error("Exception in getRole", e);
			return null;
		}
	}

	@Override
	public Response updateRole(RoleModel roleModel) throws Exception {
		Response response = CommonUtils.getResponseObject("Add Role");
		try {

			Role role = new Role();
			BeanUtils.copyProperties(roleModel, role);
			byte[] bytes = CommonUtils.getJson(roleModel.getMenu()).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			role.setMenu(blob);
			String sql = "UPDATE role SET name=?,alias=?,menu=?,organizationId=?,isActive=?, modifiedDate=? WHERE roleId=?";
			int res = jdbcTemplate.update(sql, role.getName(), role.getAlias(), role.getMenu(),
					role.getOrganizationId(), role.getIsActive(), new Date(), role.getRoleId());
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

	@Override
	public Response deleteRole(String roleId) {
		Response response = CommonUtils.getResponseObject("Delete role data");
		try {
			String sql = "DELETE FROM role  WHERE roleId=?";

			int rows = jdbcTemplate.update(sql, roleId);
			if (rows == 1) {
				response.setStatus(StatusCode.SUCCESS.name());
			} else {
				response.setStatus(StatusCode.ERROR.name());
			}
		} catch (Exception e) {
			logger.error("Exception in delete role data", e);
			response.setStatus(StatusCode.ERROR.name());
			response.setErrors(e.getMessage());
		}
		return response;
	}

	@Override
	public List<Role> getRolesByOrg(String organizationId) throws Exception {
		try {
			String sql = "SELECT * FROM role where organizationId=? ";
			List<Role> roles = jdbcTemplate.query(sql, new Object[] { organizationId },
					new BeanPropertyRowMapper<Role>(Role.class));
			return roles;
		} catch (Exception e) {
			logger.error("Exception in getRolesByOrg", e);
		}
		return null;
	}

	@Override
	public Response updateMenusByPackage(MenuTree menuTree, String packageId) throws Exception {
		Response response = CommonUtils.getResponseObject("Update Menus");
		try {

			Role role = new Role();
			byte[] bytes = CommonUtils.getJson(menuTree).getBytes();
			Blob blob = jdbcTemplate.getDataSource().getConnection().createBlob();
			blob.setBytes(1, bytes);
			role.setMenu(blob);
			String sql = "UPDATE role SET menu=? WHERE packageId=?";
			int res = jdbcTemplate.update(sql, role.getMenu(), packageId);
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
