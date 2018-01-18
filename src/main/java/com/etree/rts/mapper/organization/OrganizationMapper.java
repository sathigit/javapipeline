package com.etree.rts.mapper.organization;

import org.springframework.stereotype.Component;


import com.etree.rts.domain.organization.Organization;
import com.etree.rts.mapper.AbstractModelMapper;

import com.etree.rts.model.organization.OrganizationModel;

@Component
public class OrganizationMapper extends AbstractModelMapper<OrganizationModel, Organization> {

	@Override
	public Class<OrganizationModel> entityType() {
		
		return OrganizationModel.class;
	}

	@Override
	public Class<Organization> modelType() {
		
		return Organization.class;
	}
}
