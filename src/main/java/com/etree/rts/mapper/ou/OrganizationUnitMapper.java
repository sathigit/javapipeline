package com.etree.rts.mapper.ou;

import org.springframework.stereotype.Component;

import com.etree.rts.domain.ou.OrganizationUnit;
import com.etree.rts.mapper.AbstractModelMapper;
import com.etree.rts.model.ou.OrganizationUnitModel;

@Component
public class OrganizationUnitMapper extends AbstractModelMapper<OrganizationUnitModel, OrganizationUnit> {

	@Override
	public Class<OrganizationUnitModel> entityType() {

		return OrganizationUnitModel.class;
	}

	@Override
	public Class<OrganizationUnit> modelType() {

		return OrganizationUnit.class;
	}
}
