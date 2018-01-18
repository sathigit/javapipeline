package com.etree.rts.service.organization;

import java.util.List;

import com.etree.rts.model.organization.OrganizationModel;
import com.etree.rts.response.Response;

public interface OrganizationService {

	Response saveOrganization(OrganizationModel organization) throws Exception;

	Response updateOrganization(OrganizationModel organization) throws Exception;

	OrganizationModel getOrganization(String organizationId) throws Exception;

	List<OrganizationModel> getOrganizations() throws Exception;

	Response updateOrganizationStatus(OrganizationModel organization);
}
