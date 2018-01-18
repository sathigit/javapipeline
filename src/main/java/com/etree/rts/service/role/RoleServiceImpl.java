package com.etree.rts.service.role;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etree.rts.constant.Constants;
import com.etree.rts.dao.role.RoleDAO;
import com.etree.rts.domain.role.Role;
import com.etree.rts.mapper.role.RoleMapper;
import com.etree.rts.model.role.RoleModel;
import com.etree.rts.response.Response;

@Service("roleService")
public class RoleServiceImpl implements RoleService, Constants {
	@Autowired
	RoleDAO roleDAO;

	@Autowired
	RoleMapper roleMapper;

	private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);

	@Override
	public Response saveRole(RoleModel roleModel) throws Exception {
		try {
			Response response = roleDAO.saveRole(roleModel);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in saveRole:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public List<RoleModel> getRoles() throws Exception {
		try {
			List<Role> roles = roleDAO.getRoles();
			return roleMapper.entityList(roles);
		} catch (Exception ex) {
			logger.info("Exception getRoles:", ex);
		}
		return null;
	}

	@Override
	public RoleModel getRole(String roleId) throws Exception {
		try {
			Role role = roleDAO.getRole(roleId);
			RoleModel roleModel = new RoleModel();
			if (role == null)
				return null;
			BeanUtils.copyProperties(role, roleModel);
			return roleModel;
		} catch (Exception e) {
			logger.info("Exception in getSupplier:", e);
			return null;
		}
	}

	@Override
	public Response updateRole(RoleModel roleModel) throws Exception {
		try {
			return roleDAO.updateRole(roleModel);
		} catch (Exception ex) {
			logger.info("Exception in updateSupplier:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public Response deleteRole(String roleId) {
		try {
			return roleDAO.deleteRole(roleId);
		} catch (Exception ex) {
			logger.info("Exception in deleteRole:" + ex.getMessage());
		}
		return null;

	}
	
	@Override
	public List<RoleModel> getRolesByOrg(String organizationId) throws Exception {
		try {
			List<Role> roles = roleDAO.getRolesByOrg(organizationId);
			return roleMapper.entityList(roles);
		} catch (Exception ex) {
			logger.info("Exception getRoles:", ex);
		}
		return null;
	}

}
