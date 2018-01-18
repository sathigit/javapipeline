package com.etree.rts.mapper.packages;


import org.springframework.stereotype.Component;

import com.etree.rts.domain.packages.Packages;
import com.etree.rts.domain.role.Role;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.packages.PackagesModel;
import com.etree.rts.model.role.RoleModel;
@Component
public class PackagesMapper extends AbstractModelMapper<PackagesModel, Packages> {

	@Override
	public Class<PackagesModel> entityType() {
		
		return PackagesModel.class;
	}

	@Override
	public Class<Packages> modelType() {
		
		return Packages.class;
	}
}
