package com.etree.rts.dao.role;

import java.util.List;

import com.etree.rts.domain.role.Role;
import com.etree.rts.model.menu.MenuTree;
import com.etree.rts.model.role.RoleModel;
import com.etree.rts.response.Response;

public interface RoleDAO {

	Response saveRole(RoleModel roleModel) throws Exception;

	List<Role> getRoles() throws Exception;

	Response updateRole(RoleModel roleModel) throws Exception;

	Role getRole(String roleId) throws Exception;

	Response deleteRole(String roleId) throws Exception;

	List<Role> getRolesByOrg(String organizationId) throws Exception;

	Response saveAdminRole(Role role) throws Exception;

	Response updateMenusByPackage(MenuTree menuTree, String packageId) throws Exception;
}
