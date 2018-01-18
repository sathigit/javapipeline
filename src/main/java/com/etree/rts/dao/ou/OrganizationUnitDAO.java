package com.etree.rts.dao.ou;

import java.util.List;

import com.etree.rts.domain.ou.OrganizationUnit;
import com.etree.rts.response.Response;

public interface OrganizationUnitDAO {

	Response addOrganizationUnits(OrganizationUnit[] organizationUnits, String organizationId) throws Exception;

	public Response saveOrganizationUnit(OrganizationUnit organization) throws Exception;

	public Response updateOrganizationUnit(OrganizationUnit organization) throws Exception;

	public OrganizationUnit getOrganizationUnit(String organizationId) throws Exception;

	public List<OrganizationUnit> getOrganizationUnits() throws Exception;

	public boolean isOrganizationUnitExist(String organizationId) throws Exception;

	public List<OrganizationUnit> getOrganizationUnits(String[] uuids) throws Exception;
}
