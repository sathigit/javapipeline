package com.etree.rts.service.product;

import java.io.IOException;
import java.util.Date;

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
import com.etree.rts.dao.organization.OrganizationDAO;
import com.etree.rts.dao.product.ProductDAO;
import com.etree.rts.domain.organization.Organization;
import com.etree.rts.domain.products.Product;
import com.etree.rts.response.Response;
import com.etree.rts.utils.CommonUtils;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("productService")
public class ProductServiceImpl implements ProductService, Constants {

	@Autowired
	ProductDAO productDAO;

	@Autowired
	OrganizationDAO organizationDAO;

	@Autowired
	RTSProperties rtsProperties;

	RestTemplate restTemplate = new RestTemplate();
	ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(ProductServiceImpl.class);

	public ProductServiceImpl() {
		// TODO
	}

	public Response addProducts(String organizationId) {
		Response response = CommonUtils.getResponseObject("Add Products");
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
				UriComponents uriComponents = UriComponentsBuilder.fromUriString(rtsProperties.getKoronaProductsUrl())
						.build().expand(organization.getKoronaToken(), rtsProperties.getKoronaLimit(), offset);
				String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
				JSONObject object = new JSONObject(result);
				JSONArray productsJson = (JSONArray) object.get("resultList");
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				Product[] products = mapper.readValue(productsJson.toString(), Product[].class);
				if (products != null) {
					productDAO.addProducts(products, organizationId);
					if (products.length == limit) {
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

	public Response syncProducts(String organizationId) {
		Response response = CommonUtils.getResponseObject("Sync Products");
		try {
			Date beforeCall = new Date();
			if (!organizationDAO.isOrganizationExist(organizationId)) {
				return isOrganizationExist(organizationId, response);
			}
			if (productDAO.getProductCount(organizationId) > 0) {
				processSyncProducts(organizationId, response);
			} else {
				addProducts(organizationId);
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

	private void processSyncProducts(String organizationId, Response response)
			throws Exception, JSONException, IOException, JsonParseException, JsonMappingException {
		int offset = 0;
		int limit = rtsProperties.getSyncLimit();
		/**
		 * Get Organization Object
		 */
		Organization organization = organizationDAO.getOrg(organizationId);
		while (true) {
			UriComponents uriComponents = UriComponentsBuilder.fromUriString(rtsProperties.getKoronaProductsUpdateUrl())
					.build().expand(organization.getKoronaToken(), productDAO.getRevision(organizationId),
							rtsProperties.getSyncLimit(), offset);
			String result = restTemplate.getForObject(uriComponents.toUriString(), String.class);
			JSONObject object = new JSONObject(result);
			if (!object.isNull("resultList")) {
				JSONArray productsJson = (JSONArray) object.get("resultList");
				mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
				Product[] products = mapper.readValue(productsJson.toString(), Product[].class);
				if (products != null) {
					productDAO.addProducts(products, organizationId);
					if (products.length == limit) {
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

	public Response syncProductsIntermediate(String organizationId) {
		Response response = CommonUtils.getResponseObject("Sync Products");
		try {
			Date beforeCall = new Date();
			if (!organizationDAO.isOrganizationExist(organizationId)) {
				return isOrganizationExist(organizationId, response);
			}
			processSyncProducts(organizationId, response);
			Date afterCall = new Date();
			long difference = (afterCall.getTime() - beforeCall.getTime()) / 1000;
			logger.info("Total Time to add products is:" + difference + " seconds");
		} catch (Exception e) {
			logger.error("Exception while fetching products : " + " ", e);
			response.setStatus(StatusCode.ERROR.name());
		}
		return response;
	}

	private Response isOrganizationExist(String organizationId, Response response) throws Exception {
		response.setMessage("Organization Id[" + organizationId + "] does not exist");
		response.setStatus(StatusCode.ERROR.name());
		return response;
	}
}
