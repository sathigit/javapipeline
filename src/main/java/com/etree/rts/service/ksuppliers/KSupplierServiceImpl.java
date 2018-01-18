package com.etree.rts.service.ksuppliers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.etree.rts.config.RTSProperties;
import com.etree.rts.constant.Constants;
import com.etree.rts.constant.StatusCode;
import com.etree.rts.dao.ksuppliers.KSupplierDAO;
import com.etree.rts.dao.organization.OrganizationDAO;
import com.etree.rts.domain.ksuppliers.KSupplier;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("kSupplierService")
public class KSupplierServiceImpl implements KSupplierService, Constants {

	@Autowired
	KSupplierDAO kSupplierDAO;

	@Autowired
	OrganizationDAO organizationDAO;

	@Autowired
	RTSProperties rtsProperties;

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(KSupplierServiceImpl.class);

	public KSupplierServiceImpl() {
		// TODO
	}

	public Response addKSuppliers(String organizationId) {
		Response response = CommonUtils.getResponseObject("Add KSuppliers");
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
				UriComponents uriComponents = UriComponentsBuilder.fromUriString(rtsProperties.getKoronaSuppliersUrl())
						.build().expand(organization.getKoronaToken(), rtsProperties.getKoronaLimit(), offset);
				String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
				JSONObject object = new JSONObject(result);
				if (object.get("resultList") instanceof JSONArray) {
					JSONArray kSupplierssJson = (JSONArray) object.get("resultList");
					mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
					KSupplier[] kSuppliers = mapper.readValue(kSupplierssJson.toString(), KSupplier[].class);
					if (kSuppliers != null) {
						kSupplierDAO.addKSuppliers(kSuppliers, organizationId);
						if (kSuppliers.length == limit) {
							offset += limit;
						} else {
							response.setStatus(StatusCode.SUCCESS.name());
							break;
						}
					}
				} else {
					response.setStatus(StatusCode.SUCCESS.name());
					break;
				}
			}
			Date afterCall = new Date();
			long difference = (afterCall.getTime() - beforeCall.getTime()) / 1000;
			logger.info("Total Time to add kSuppliers is:" + difference + " seconds");
		} catch (Exception e) {
			logger.error("Exception while fetching kSuppliers : " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
		}
		return response;
	}

	@Override
	public Response syncKSuppliers(String organizationId) {
		Response response = CommonUtils.getResponseObject("Sync kSuppliers");
		try {
			Date beforeCall = new Date();
			if (!organizationDAO.isOrganizationExist(organizationId)) {
				return isOrganizationExist(organizationId, response);
			}
			if (kSupplierDAO.getKSupplierCount(organizationId) > 0) {
				processSyncKSuppliers(organizationId, response);
			} else {
				addKSuppliers(organizationId);
			}
			Date afterCall = new Date();
			long difference = (afterCall.getTime() - beforeCall.getTime()) / 1000;
			logger.info("Total Time to add kSuppliers is:" + difference + " seconds");
		} catch (Exception e) {
			logger.error("Exception while fetching kSuppliers : " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
		}
		return response;
	}

	private void processSyncKSuppliers(String organizationId, Response response)
			throws Exception, JSONException, IOException, JsonParseException, JsonMappingException {
		int offset = 0;
		int limit = rtsProperties.getSyncLimit();
		/**
		 * Get Organization Object
		 */
		Organization organization = organizationDAO.getOrg(organizationId);
		while (true) {
			UriComponents uriComponents = UriComponentsBuilder
					.fromUriString(rtsProperties.getKoronaSuppliersUpdateUrl()).build()
					.expand(organization.getKoronaToken(), kSupplierDAO.getRevision(organizationId),
							rtsProperties.getSyncLimit(), offset);
			String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
			JSONObject object = new JSONObject(result);
			if (!object.isNull("resultList")) {
				JSONArray productsJson = (JSONArray) object.get("resultList");
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				KSupplier[] kSuppliers = mapper.readValue(productsJson.toString(), KSupplier[].class);
				if (kSuppliers != null) {
					kSupplierDAO.addKSuppliers(kSuppliers, organizationId);
					if (kSuppliers.length == limit) {
						offset += limit;
					} else {
						response.setStatus(StatusCode.SUCCESS.name());
						break;
					}
				}
			} else {
				break;
			}
		}
	}

	private Response isOrganizationExist(String organizationId, Response response) throws Exception {
		response.setMessage("Organization Id[" + organizationId + "] does not exist");
		response.setStatus(StatusCode.ERROR.name());
		return response;
	}

	@Override
	public List<KSupplier> getKSuppliers(String organizationId) {
		try {
			return kSupplierDAO.getKSuppliers(organizationId);
		} catch (Exception ex) {
			logger.info("Exception getKSuppliers:", ex);
		}
		return null;
	}

}
