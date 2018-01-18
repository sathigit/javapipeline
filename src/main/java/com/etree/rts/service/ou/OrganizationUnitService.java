package com.etree.rts.service.ou;

import java.util.List;

import com.etree.rts.model.ou.OrganizationUnitModel;
import com.etree.rts.response.Response;

public interface OrganizationUnitService {

	Response addOrganizationUnits(String organizationId) throws Exception;
	
	Response saveOrganizationUnit(OrganizationUnitModel organization) throws Exception;

	Response updateOrganizationUnit(OrganizationUnitModel organization) throws Exception;

	OrganizationUnitModel getOrganizationUnit(String organizationId) throws Exception;

	List<OrganizationUnitModel> getOrganizationUnits() throws Exception;
}
