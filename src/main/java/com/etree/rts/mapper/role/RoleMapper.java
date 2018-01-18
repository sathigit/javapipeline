package com.etree.rts.mapper.role;


import org.springframework.stereotype.Component;

import com.etree.rts.domain.role.Role;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.role.RoleModel;
@Component
public class RoleMapper extends AbstractModelMapper<RoleModel, Role> {

	@Override
	public Class<RoleModel> entityType() {
		
		return RoleModel.class;
	}

	@Override
	public Class<Role> modelType() {
		
		return Role.class;
	}
}
