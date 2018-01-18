package com.etree.rts.service.ou;

import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.organization.OrganizationDAO;
import com.etree.rts.dao.ou.OrganizationUnitDAO;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.domain.ou.OrganizationUnit;
import com.etree.rts.mapper.ou.OrganizationUnitMapper;
import com.etree.rts.model.ou.OrganizationUnitModel;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("organizationUnitService")
public class OrganizationUnitServiceImpl implements OrganizationUnitService, Constants {

	private static final Logger logger = LoggerFactory.getLogger(OrganizationUnitServiceImpl.class);
	@Autowired
	OrganizationUnitDAO organizationUnitDAO;
	@Autowired
	OrganizationDAO organizationDAO;
	@Autowired
	OrganizationUnitMapper organizationUnitMapper;

	@Autowired
	RTSProperties rtsProperties;

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();

	public Response addOrganizationUnits(String organizationId) {
		Response response = CommonUtils.getResponseObject("Add Organization Units");
		try {
			if (!organizationDAO.isOrganizationExist(organizationId)) {
				return isOrganizationExist(organizationId, response);
			}
			int offset = 0;
			int limit = rtsProperties.getKoronaLimit();
			Date beforeCall = new Date();
			/**
			 * Get Organization Object
			 */
			Organization organization = organizationDAO.getOrg(organizationId);
			while (true) {
				UriComponents uriComponents = UriComponentsBuilder
						.fromUriString(rtsProperties.getKoronaOrganizationalUnitsUrl()).build()
						.expand(organization.getKoronaToken(), rtsProperties.getKoronaLimit(), offset);
				String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
				JSONObject object = new JSONObject(result);
				JSONArray productsJson = (JSONArray) object.get("resultList");
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				OrganizationUnit[] organizationUnits = mapper.readValue(productsJson.toString(),
						OrganizationUnit[].class);
				if (organizationUnits != null) {
					organizationUnitDAO.addOrganizationUnits(organizationUnits, organizationId);
					if (organizationUnits.length == limit) {
						offset += limit;
					} else {
						response.setStatus(StatusCode.SUCCESS.name());
						break;
					}
				}
			}
			Date afterCall = new Date();
			long difference = (afterCall.getTime() - beforeCall.getTime()) / 1000;
			logger.info("Total Time to add products is:" + difference + " seconds");
		} catch (Exception e) {
			logger.error("Exception while fetching products : " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
		}
		return response;
	}

	@Override
	public Response saveOrganizationUnit(OrganizationUnitModel oModel) throws Exception {
		try {
			OrganizationUnit organization = new OrganizationUnit();
			BeanUtils.copyProperties(oModel, organization);
			organization.setIsActive(true);
			Response response = organizationUnitDAO.saveOrganizationUnit(organization);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in saveOrganization:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public Response updateOrganizationUnit(OrganizationUnitModel oModel) throws Exception {
		try {
			OrganizationUnit organization = new OrganizationUnit();
			BeanUtils.copyProperties(oModel, organization);
			Response response = organizationUnitDAO.updateOrganizationUnit(organization);
			return response;
		} catch (Exception ex) {
			logger.info("Exception in updateOrganization:" + ex.getMessage());
		}
		return null;
	}

	@Override
	public OrganizationUnitModel getOrganizationUnit(String uuid) throws Exception {
		try {
			OrganizationUnit organization = organizationUnitDAO.getOrganizationUnit(uuid);
			OrganizationUnitModel organizationModel = new OrganizationUnitModel();
			if (organization == null)
				return null;
			BeanUtils.copyProperties(organization, organizationModel);
			return organizationModel;
		} catch (Exception e) {
			logger.info("Exception in getOrganization:", e);
			return null;
		}

	}

	@Override
	public List<OrganizationUnitModel> getOrganizationUnits() {
		try {
			List<OrganizationUnit> organizations = organizationUnitDAO.getOrganizationUnits();
			return organizationUnitMapper.entityList(organizations);
		} catch (Exception ex) {
			logger.info("Exception getTemplates:", ex);
		}
		return null;
	}

	private Response isOrganizationExist(String organizationId, Response response) throws Exception {
		response.setMessage("Organization Id[" + organizationId + "] does not exist");
		response.setStatus(StatusCode.ERROR.name());
		return response;
	}
}
