package com.etree.rts.dao.organization;

import java.util.List;

import com.etree.rts.domain.organization.Organization;
import com.etree.rts.response.Response;

public interface OrganizationDAO {

	public Response saveOrganization(Organization organization) throws Exception;

	public Response updateOrganization(Organization organization) throws Exception;

	public Organization getOrganization(String organizationId) throws Exception;
	
	public Organization getOrg(String organizationId) throws Exception;
	
	public Organization getOrganization(String organizationId, String userId) throws Exception;

	public List<Organization> getOrganizations() throws Exception;

	public boolean isOrganizationExist(String organizationId) throws Exception;
	
	public Response deleteOrganization(String organizationId) throws Exception;

	public Response updateOrganizationStatus(Organization organization);
}
