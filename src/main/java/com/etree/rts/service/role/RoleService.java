package com.etree.rts.service.role;

import java.util.List;

import com.etree.rts.model.role.RoleModel;
import com.etree.rts.response.Response;

public interface RoleService {

	public Response saveRole(RoleModel roleModel) throws Exception;

	public List<RoleModel> getRoles() throws Exception;

	public Response updateRole(RoleModel roleModel) throws Exception;

	public RoleModel getRole(String roleId) throws Exception;

	public Response deleteRole(String roleId) throws Exception;
	
	public List<RoleModel> getRolesByOrg(String organizationId) throws Exception;

}
